import kurt
import os
import shutil

label_whenReceive = "when I receive %s"

Codeic_setBackgroundColor = "Codeic_setBackgroundColor"
Codeic_regeisterEvent = "Codeic_regeisterEvent"
Codeic_regeisterSprite = "Codeic_regeisterSprite"

#const value
build_path = ".\\build"
#global value
eventID = 0
valueID = 0
spriteID = 0
variableDict = {};
spriteDict = {};

def tostring(o):
	if isinstance(o,int):
		return tostring(str(o))
	return o.encode('utf-8')

def getVariableID(name):
	return tostring(variableDict[name])
	
def getVariableName(name):
	return 'CodeicVar_{}'.format(getVariableID(name))

def setVariable(name,value):
	global valueID
	variableDict[name] = valueID
	valueID = valueID + 1
	return 'CodeicVar_{}="{}"--{}\n'.format(getVariableID(name),tostring(value),name)
	
def setList(name,list):
	global valueID
	variableDict[name] = valueID
	valueID = valueID +1
	result = "CodeicVar_"+getVariableID(name) + "={"
	hasElement = 0
	for item in list.items:
		hasElement = 1
		result += "\"" + tostring(item) + "\","
	
	if hasElement:result = result[:-1]
	
	
	result = result + "}\n"
	return result
	
def setEvent(script,message):#Set global(stage) event
	global eventID
	result = '{}({},"{}")\n'.format(Codeic_regeisterEvent,tostring(eventID),message)#Register event
	result += 'function Codeic_Event_{}()\n'.format(tostring(eventID))
	for i in range(1,len(script.blocks)):
		scr = script.blocks[i].stringify() + "\n"
		result += scr
	
	result += "end\n"
	eventID = eventID+1
	return tostring(result)
	
def dumpSprite(sprite):
	global spriteID
	global valueID
	spriteDict[sprite.name] = spriteID
	spriteID = spriteID+1
	result = 'Codeic_{}=--{}\n'.format(spriteDict[sprite.name],tostring(sprite.name))
	result += '{\n'
	#Variable
	hasVariable = 0
	for variable in sprite.variables:
		hasVariable = 1
		variableDict[tostring(variable)] = valueID
		valueID = valueID + 1
		result += 'CodeicVar_{}="{}"--[[{}]],\n'.format(valueID,tostring(sprite.variables[variable].value),tostring(variable))
		
	if hasVariable:
		result = result[:-2]#remove the last comma and '\n'
		result += '\n'
	
	result += '}\n'
	
	return result
	
#Create build directory
print "Loading scratch project file"
if os.path.exists(build_path):shutil.rmtree(build_path)	
	
os.mkdir(build_path)

#Files object
global_script = open(build_path+"\\global.lua",'w')


#Get object
project = kurt.Project.load("E:\zhuom\game.sb2")
#stage
stage = project.stage
global_script.write(Codeic_setBackgroundColor+str(stage.COLOR)+"\n")
for variable in project.variables:
	global_script.write(setVariable(tostring(variable),project.variables[variable].value))
	
for list in project.lists:
	if not list=="":global_script.write(setList(tostring(list),project.lists[list]))	

eventID = 0
for script in stage.scripts:
	if script.blocks[0].type.text == label_whenReceive:
		global_script.write(setEvent(script,script.blocks[0].args[0]))
		
for sound in stage.sounds:
	sound.save(build_path+"\\"+sound.name+".wav")

for costume in stage.costumes:
	costume.save(build_path+"\\"+costume.name+".png")

#Sprite
spriteID = 0
for sprite in project.sprites:
	global_script.write(dumpSprite(sprite))

#Close
global_script.flush()
global_script.close()

print "Done!"