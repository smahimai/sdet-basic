"""
School - Derived Class from Student
Inherits from Student class and adds school-specific functionality
"""
from Student import Student

class School(Student):
    def __init__(self, name, grade, age, school_name=None):
        super().__init__(name, grade, age)
        self.school_name = school_name
    
    def SchoolStudentDisplay(self):
        print("=== School Student Information ===")
        print(f"Name: {self.Name}")
        print(f"Grade: {self.Grade}")
        print(f"Age: {self.Age}")
        if self.school_name:
            print(f"School: {self.school_name}")

