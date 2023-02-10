
I created the class by myself. It can be downloaded at: https://jbox.sjtu.edu.cn/l/3Jv98I
javap -verbose CTest.class
public class CTest
minor version: 0
major version: 52
flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
#1 = Utf8 CTest
#2 = Class #1 // CTest
#3 = Utf8 java/lang/Object
#4 = Class #3 // java/lang/Object
#5 = Utf8 f
#6 = Utf8 ()I
#7 = Utf8 f2
#8 = Utf8 ([Ljava/lang/String;)V
#9 = Utf8 java/lang/ClassNotFoundException
#10 = Class #9 // java/lang/ClassNotFoundException
#11 = NameAndType #5:#6 // f:()I
#12 = Methodref #2.#11 // CTest.f:()I
#13 = Utf8 Ljava/lang/Object;
#14 = Class #13 // "Ljava/lang/Object;"
#15 = Utf8 Code
#16 = Utf8 StackMapTable
{
static void f2(java.lang.String[]);
descriptor: ([Ljava/lang/String;)V
flags: ACC_STATIC
Code:
stack=2, locals=2, args_size=1
0: goto 23
3: invokestatic #12 // Method f:()I
6: ifne 23
9: aload_0
10: iconst_0
11: aaload
12: astore_0
13: return
14: astore_1
15: aload_0
16: iconst_0
17: aaload
18: astore_1
19: invokestatic #12 // Method f:()I
22: pop
23: goto 3
Exception table:
from to target type
3 13 14 Class java/lang/ClassNotFoundException
StackMapTable: number_of_entries = 3
frame_type = 255 /* full_frame /
offset_delta = 3
locals = [ class "Ljava/lang/Object;" ]
stack = []
frame_type = 74 / same_locals_1_stack_item /
stack = [ class java/lang/ClassNotFoundException ]
frame_type = 8 / same */
}
