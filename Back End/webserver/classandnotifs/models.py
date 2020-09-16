from django.db import models
from users.models import Teacher, Student
from django.utils import timezone

# Create your models here.

class Classroom(models.Model):
    name = models.CharField(max_length=100)
    teacher = models.ForeignKey(to=Teacher, on_delete=models.CASCADE)
    date_created = models.DateTimeField(timezone.now)

class Notification(models.Model):
    date_created = models.DateTimeField(default=timezone.now)
    title = models.CharField(max_length=40)
    subject = models.CharField(max_length=50)
    content = models.TextField()
    deadline = models.DateTimeField(default=timezone.now)
    classroom= models.ForeignKey(to=Classroom, on_delete=models.CASCADE)

class Studnet_Classroom(models.Model):
    student = models.ForeignKey(to=Student, on_delete=models.CASCADE)
    classroom = models.ForeignKey(to=Classroom, on_delete=models.CASCADE)