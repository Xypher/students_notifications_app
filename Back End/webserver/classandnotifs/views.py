from django.shortcuts import render
from django.http import JsonResponse
from .models import *
from users.models import Student, Teacher
import datetime

from django.core.mail import send_mail
from django.conf import settings

def get_classrooms(request, username, type):

    json = {
        "success":True,
        "errors": {},
        "result":{"classrooms" : []}
    }

    if type == "student": 
        student = Student.objects.get(username=username)
        relations = Studnet_Classroom.objects.filter(student=student)
        

        for relation in relations:
            json["result"]["classrooms"].append({
                    "class": {
                        "name" : relation.classroom.name,
                        "teacher_name" : relation.classroom.teacher.fullname
                    }
                }
            )

    if type == "teacher": 
        teacher = Teacher.objects.get(username=username)
        classes = Classroom.objects.filter(teacher=teacher)

        for clas in classes:
            json["result"]["classrooms"].append({
                    "class": {
                        "name" : clas.name,
                        "teacher_name" : clas.teacher.fullname
                    }
                }
            )

        
    return JsonResponse(data=json, safe=False)




def get_notifications_by_user(request, username, type):

    json = {
        "success":True,
        "errors": {},
        "result":{"notifications" : []}
    }

    notifs = []
    if type == "student":
        student = Student.objects.get(username=request["username"])
        crelations = Studnet_Classroom.objects.filter(student=student)
        classes = [rel.classroom for rel in crelations]

        
        for cls in classes:
            for notif in Notification.objects.filter(classroom=cls):
                notifs.append(notif); # fuck_my_life = 5

    else:
        teacher = Teacher.objects.get(username=request["username"])   
        classes = Classroom.objects.filter(teacher=teacher)
        for cls in classes:
            for notif in Notification.objects.filter(classroom=cls):
                notifs.append(notif); # fuck_my_life = 5

    for notif in notifs:
        json["result"]["notifications"].append({

            "notif" : {
                "title" : notif.title,
                "subject" : notif.subject,
                "content" : notif.content,
                "deadline" : notif.deadline
            }

        })

    return JsonResponse(data=json, safe=False)


def get_notifications_by_class(request, name):


    classroom = Classroom.objects.get(name=name)
    
    json = {
        "success":True,
        "errors": {},
        "result":{"notifications" : []}
    }

    notifs = Notification.objects.filter(classroom=classroom)
    teacher = classroom.teacher

    for notif in notifs:
        json["result"]["notifications"].append({

            "notif" : {
                "teacher" : teacher.fullname,
                "title" : notif.title,
                "subject" : notif.subject,
                "content" : notif.content,
                "deadline" : notif.deadline
            }

        })
        
    return JsonResponse(data=json, safe=False)


def add_student_to_class(request, _id, class_name):
    #
    classroom = Classroom.objects.get(name=class_name)
    
    json = {
        "success":True,
        "errors": [],
        "result":{}
    }

    if not Student.objects.filter(student_id=_id).exists():
        json["success"] = False
        json["errors"].append({"message": "no student with such ID"})

    if json["success"]:
        student = Student.objects.get(_id=_id) 
        Studnet_Classroom.objects.create(student=student, classroom=classroom)

    return JsonResponse(data=json, safe=False)

        



def create_class(request, teacher_name, class_name):
    teacher = Teacher.objects.get(username=teacher_name)

    json = {
        "success":True,
        "errors": [],
        "result":dict()
    } 

    if Classroom.objects.get(name= class_name) != None:
        json["success"] = False
        json["errors"].append({"message" : "the class name is already taken"})
        return JsonResponse(data=json, safe=False)

    Classroom.objects.create(teacher=teacher, name=class_name)


    return JsonResponse(data=json, safe=False)
    

def create_notification(request, class_name, title, subject, content, dead_year, dead_month, dead_day, dead_hour, dead_min):
    

    deadline = datetime.datetime(int(dead_year), int(dead_month), int(dead_day), int(dead_hour), int(dead_min))
    classroom = Classroom.objects.get(name=class_name)
    Notification.objects.create(title=title, subject=subject, content=content, deadline=deadline, classroom=classroom)

    

    json = {
        "success":True,
        "errors": [],
        "result":dict()
    } 


    students = [rel.student for rel in Studnet_Classroom.objects.filter(classroom=classroom)]

    for student in students:
        
        message = content
        email_from = settings.EMAIL_HOST_USER
        recipient_list = [student.email,]
        send_mail("to" + student.fullname  + "\n" + subject, message, email_from, recipient_list, fail_silently=False)


    return JsonResponse(data=json, safe=False)
