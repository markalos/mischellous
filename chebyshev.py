import numpy as np
import matplotlib.pyplot as plt
import sys
import os
import time

def chebyshevFitting(x, y, degree):
	'''
		returns : a least square fitting function
	'''
	return np.polynomial.Chebyshev.fit(x, y, degree)

def lossFunc(target, approximation):
	error_square = np.sum(np.square(target - approximation))
	normalized = np.sqrt(error_square / (len(target)))
	return  normalized

def bestFitting(x, y, toDegree, startDegree = 0):
	if toDegree < 0:
		raise ValueError("polynominal degree must be non-negtive!")
	toDegree += 1
	delta = [0.0] * (toDegree - startDegree)
	for i in xrange(startDegree, toDegree):
		p = chebyshevFitting(x, y, i)
		delta[i - startDegree] = lossFunc(y, p(x))
	return delta

def writeRes(res):
	folder = 'res'
	if not os.path.exists(folder):
		os.makedirs(folder)
	desfile = folder + '/' + time.strftime("%Y%m%d_%H%M%S") + '.txt'
	with open(desfile, 'w') as toWrite:
		toWrite.write(str(res))

def plotit(x, res):
	plt.plot(x, res)
	plt.xlim(x[0], x[-1])
	plt.show()

def main(dataFile, degree, startDegree, separator):
	try:
		fdes = open(dataFile)
		x = []
		y = []
		for line in fdes :
			point = [float(d) for d in line.split(separator)]
			if 2 != len(point):
				print 'A [x y] value must and should be provided per line!'
				return
			x.append(point[0])
			y.append(point[1])
		x = np.array(x)
		y = np.array(y)
		delta = bestFitting(x, y, degree, startDegree = startDegree)
		degrees = range(startDegree, degree + 1)
		writeRes(delta)
		plotit(degrees, delta)
	except Exception as e:
		print e
		raise e
	finally:
		pass

if __name__ == '__main__':
	arguments = sys.argv
	if len(arguments) < 3:
		print 'too little argumemnts!'
		print 'usage : python script.py filename degree [startDegree] [seperator]'
	else :
		dataFile = arguments[1]
		degree = int(arguments[2])
		separator = ' '
		startDegree = 0
		if len(arguments) > 3:
			startDegree = int(arguments[3])
		if len(arguments) > 4:
			separator = arguments[4]
		main(dataFile, degree , separator = separator, startDegree = startDegree)