import os

def listFileInDir(folder):
	files = []
	for file in os.listdir(folder) :
		if file.endswith(".txt") :
			files.append(os.path.join(folder, file))

	return files


if __name__ == '__main__':
	pass