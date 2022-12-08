from pulp import *

# Create the problem variable to contain the problem data
model = LpProblem("ViewershipProblem", LpMaximize)

x1 = LpVariable("x1", 0, None, LpInteger)
x2 = LpVariable("x2", 0, None, LpInteger) 
x3 = LpVariable("x3", 0, None, LpInteger)

model += 15000 * x1 + 25000 * x2 + 18000 * x3 

model += x1 + x2 + x3 <= 15, "NumberOfVideos"
model += 1200 * x1 + 2200 * x2 + 1500 * x3 <= 26000, "MoneyToSpend"

model.solve()

# Each of the variables is printed with it's resolved optimum value
print("PRIMAL: ")
for v in model.variables():
    print(v.name, "=", v.varValue)

####################

dual = LpProblem("ViewershipProblem", LpMinimize)

y1 = LpVariable("y1", 0, None, LpInteger)
y2 = LpVariable("y2", 0, None, LpInteger) 
x3 = LpVariable("y3", 0, None, LpInteger)

dual += 15 * y1 + 26000 * y2

dual += y1 + 1200 * y2 >= 15000, "ogr1"
dual += y1 + 2200 * y2 >= 25000, "ogr2"
dual += y1 + 1500 * y2 >= 18000, "ogr3"

dual.solve()

# Each of the variables is printed with it's resolved optimum value
print("DUAL: ")
for d in dual.variables():
    print(d.name, "=", d.varValue)