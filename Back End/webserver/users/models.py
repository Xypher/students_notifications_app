from django.db import models
from django.utils import timezone
from push_notifications.models import GCMDevice

# Create your models here.

class Student(models.Model):
    username = models.CharField(max_length=25)
    fullname = models.CharField(max_length=40)
    email = models.EmailField(max_length=50)
    student_id = models.CharField(max_length=10)
    password = models.CharField(max_length=16)
    time_joined = models.DateTimeField(default=timezone.now)
    

class Teacher(models.Model):
    username = models.CharField(max_length=25)
    fullname = models.CharField(max_length=40)
    email = models.EmailField(max_length=50)
    teacher_id = models.CharField(max_length=10)
    password = models.CharField(max_length=16)
    time_joined = models.DateTimeField(default=timezone.now)