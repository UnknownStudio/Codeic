import kurt
import os
import shutil

label_whenReceive = "when I receive %s"

Codeic_setBackgroundColor = "Codeic_setBackgroundColor"
Codeic_regeisterEvent = "Codeic_regeisterEvent"
Codeic_regeisterSpriteEvent = "Codeic_regeisterSpriteEvent"
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
	print type(o)
	if isinstance(o,int):
		return tostring(str(o))
	return o.encode('utf-8').strip()

def getVariableID(name):
	return tostring(variableDict[name])
	
def getVariableName(name):
	return u'CodeicVar_{}'.format(getVariableID(name))

def setVariable(name,value):
	global valueID
	variableDict[name] = valueID
	valueID = valueID + 1
	return 'CodeicVar_{}="{}"--[[{}]]'.format(getVariableID(name),tostring(value),name)
	
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
	result = '{}({},"{}")\n'.format(Codeic_regeisterEvent,'"Codeic_Event_{}"'.format(tostring(eventID)),message)#Register event
	result += 'function Codeic_Event_{}()\n'.format(tostring(eventID))
	for i in range(1,len(script.blocks)):
		scr = script.blocks[i].stringify() + "\n"
		result += scr
	
	result += "end\n"
	eventID = eventID+1
	return tostring(result)
	
def setSpriteEvent(sprite,script,message):#Set sprite event
	global eventID
	result = u'{}({},"{}")\n'.format(Codeic_regeisterEvent,u'"Codeic_{}_Event_{}"'.format(spriteDict[sprite.name],tostring(eventID)),message)#Register event
	result += 'function Codeic_{}_Event_{}()\n'.format(spriteDict[sprite.name],tostring(eventID))
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
	spriteClassName = 'Codeic_{}'.format(spriteDict[sprite.name])
	result = '{}=--{}\n'.format(spriteClassName,tostring(sprite.name))
	result += '{\n'
	hasVariable = 0
	#Variable
	for variable in sprite.variables:
		hasVariable = 1
		result += setVariable(tostring(variable),sprite.variables[variable].value) + ","

	#List
	for list in sprite.lists:
		if not list=="":
			result+=(setList(tostring(list),sprite.lists[list])) + ","
	
	
	if hasVariable:
		result = result[:-1]#remove the last comma and '\n'
		result += '\n'		
	
	#Define End
	result += '}\n'
	
	#Script
	for script in sprite.scripts:
		if script.blocks[0].type.text == label_whenReceive:
			result += setSpriteEvent(sprite,script,script.blocks[0].args[0])
	
	result += "\n"
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
	global_script.write(setVariable(tostring(variable),project.variables[variable].value)+"\n")
	
for list in project.lists:
	if not list=="":global_script.write(setList(tostring(list),project.lists[list]))	

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