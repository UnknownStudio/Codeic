import kurt
import os
import shutil

label_whenReceive = "when I receive %s"

Codeic_setBackgroundColor = "Codeic_setBackgroundColor"
Codeic_regeisterEvent = "Codeic_regeisterEvent"

#global value
eventID = 0

def tostring(o):
	if isinstance(o,int):
		return tostring(str(o))
	return o.encode('utf-8')
	
def setVariable(name,value):
	return "CodeicVar_"+tostring(name)+"=\""+tostring(value)+"\"\n"
	
def setList(name,list):
	result = "CodeicVar_"+name + "={"
	for item in list.items:
		result += "\"" + tostring(item) + "\","
	
	result = result[:-1]
	result = result + "}\n"
	return result
	
def setEvent(script,message):
	global eventID
	result = Codeic_regeisterEvent + "(" + tostring(eventID) + ",\"" + message + "\")\n"#Register event
	result = result + "function " + "Codeic_Event_" + tostring(eventID) + "()\n"
	for i in range(1,len(script.blocks)):
		result = result + script.blocks[i].stringify() + "\n"
	
	result += "end"
	eventID = eventID+1
	return tostring(result)
	
	
build_path = ".\\build"

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
	global_script.write(setVariable(variable,project.variables[variable].value))
	
for list in project.lists:
	if not list=="":global_script.write(setList(tostring(list),project.lists[list]))	

for script in stage.scripts:
	if script.blocks[0].type.text == label_whenReceive:
		global_script.write(setEvent(script,script.blocks[0].args[0]))

#Close
global_script.flush()
global_script.close()

print "Done!"
