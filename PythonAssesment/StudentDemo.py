"""
StudentDemo - Demonstration class to test Student and School classes
"""
from Student import Student
from School import School

def main():
    
    
    students = [
        Student("Charlie Brown", "C", 19),
        School("Diana Prince", "A", 16, "Metropolis Academy")
    ]
    
    for i, student in enumerate(students, 1):
        print(f"Student {i}:")
        student.display()
        print()

if __name__ == "__main__":
    main()

