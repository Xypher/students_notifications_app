from django.shortcuts import render
from django.http import JsonResponse
from .models import Student, Teacher
# Create your views here.


def login(request, username, password):

    if request.method == "GET":
        
        

        if Student.objects.filter(username=username).exists():
            user = Student.objects.get(username=username)
            if password == user.password:
                json = {
                    "success" : True,
                    "errors" : [],
                    "result" : {

                        "username" : username,
                        "password" : password,
                        "type" : "student",
                    }
                }

                return JsonResponse(data=json, safe=False)

        
        

        if Teacher.objects.filter(username=username).exists():
            user = Teacher.objects.get(username=username)
            if password == user.password:
                json = {
                    "success" : True,
                    "errors" : [],
                    "result" : {

                        "username" : username,
                        "password" : password,
                        "type" : "teacher",
                    }
                }

                return JsonResponse(data=json, safe=False)

    json = {
                "success" : False,
                "errors" : [],
                "result" : {}
            }

    return JsonResponse(data= json, safe=False)
       


def signup(request, username, password, fullname, _id, email, type):

        json = { "success" : True,
                        "errors" : [],
                        "result" : {} 
                        }

        if request.method == "GET":
            

            model = Student if type == "student" else Teacher
            

            if Teacher.objects.filter(username=username).exists():
                json["success"] = False
                json["errors"].append({"message" :"username already taken"})

            if Student.objects.filter(username=username).exists():
                json["success"] = False
                json["errors"].append({"message" :"username already taken"})
                

            if model.objects.filter(email=email).exists():
                json["success"] = False
                json["errors"].append({"message" : "email already used"})

            if model == Student and  model.objects.filter(student_id=_id).exists():
                json["success"] = False
                json["errors"].append({"message" : "id already used"})
            
            
            if model == Teacher and  model.objects.filter(teacher_id=_id).exists():
                json["success"] = False
                json["errors"].append({"message" : "id already used"})

            
            if type == "student" and json["success"]:
                Student.objects.create(username=username, fullname=fullname, student_id=_id, email=email, password=password)


            if type == "teacher" and json["success"]:
                Teacher.objects.create(username=username, fullname=fullname, teacher_id=_id, email=email, password=password)
                

            return JsonResponse(data=json, safe=False)

        json["success"] = False
        return JsonResponse(data=json, safe=False)