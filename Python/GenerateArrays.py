import numpy as np
import json

#a0=np.array([1,2,3,4,5,6])

#$input={'a0':[3,4,5,6,7,8],'a1':[2,4,5,6,7,8]}

#ilename ='test.json'
#with open(filename,'w') as file_object:
	#json.dump(input,file_object)

def writeToJsonFile(path, filename,data):
	filePathName =path +'/'+filename +'.json'
	with open(filePathName,'w') as FP:
		json.dump(data,FP)

path = '/home/paiche/HpLp_Chisel/Python'
filename = 'mulfile'

data={}
data['a0']= [1,2,3,4]
data['a1']=[2,3,4,5]
x= np.array(data['a0'])
y=np.array(data['a1'])
out = x*y
data['out']=out.tolist()
writeToJsonFile(path,filename,data)
