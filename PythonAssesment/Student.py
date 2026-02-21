"""
Student - Base Class
Contains student information: Name, Grade, and Age
"""
class Student:
    def __init__(self, name, grade, age):
        self.Name = name
        self.Grade = grade
        self.Age = age
    
    def display(self):
        print("=== Student Information ===")
        print(f"Name: {self.Name}")
        print(f"Grade: {self.Grade}")
        print(f"Age: {self.Age}")

