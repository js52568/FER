import sys
import numpy as np

#zadatak 1

names = ["Ana", "Petar", "Ana", "Lucija", "Vanja", "Pavao", "Lucija"]

def reverse_sort(names :list) -> list:
    names.sort(reverse=True)
    return names

names_desc = reverse_sort(names)
selected_names = names_desc[:-1]
unique_selected_names = set(selected_names)
pass_names = []
for i in unique_selected_names:
    name = i + "-pass"
    pass_names.append(name)

#zadatak2

person_data = {
  "Ana": 1995,
  "Zoran": 1978,
  "Lucija": 2001,
  "Anja": 1997
}

for k, v in person_data.items():
    person_data[k] = v-1

year_age = []

for k, v in person_data.items():
    year_age.append((v,2022-v))

#zadatak3

vector_a = np.array([1, 3, 5])
vector_b = np.array([[2], [4], [6]])

mat_mul = np.matmul(vector_a,vector_b)
vect_dot = np.dot(vector_a,vector_b)
mat_exp = mat_mul ** 2
#ne kuzim

#zadatak4

class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age
    def increase_age(self):
        self.age += 1

first_person = Person(name = "Marko", age = 39)
second_person = Person(name = "Ivan", age = 17)

second_person.increase_age()

class PersonDetail(Person):
    def __init__(self,name,age,adress):
        super().__init__(name,age)
        self.adress = adress

first_person_detail = PersonDetail(name="Ana",age=25,adress="Unska 3")
first_person_detail.increase_age()
