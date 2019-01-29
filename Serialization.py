import pickle

class ObjectPickle():
	def __init__(self):
		pass

	def saveObject(object, file):
		with open(file, 'wb') as f :
			pickle.dump(object, f)

	def loadObject(file):
		with open(file, 'rb') as f:
			return pickle.load(f)

def main():
	obj = ((2, 3), (1, 3, 5))
	print ('object', obj)
	ObjectPickle.saveObject(obj, 'obj')
	deserialized = ObjectPickle.loadObject('obj')
	print (deserialized)

if __name__ == '__main__':
	main()