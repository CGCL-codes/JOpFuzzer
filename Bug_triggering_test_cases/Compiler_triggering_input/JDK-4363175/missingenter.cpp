#include <jvmpi.h>
#include <cstring>
#include <iostream>

#define JVMPI(m) (jvmpi->m)

static JVMPI_Interface *jvmpi;
static void notify(JVMPI_Event *);

extern "C" JNIEXPORT jint JNICALL
JVM_OnLoad(JavaVM *jvm, char *options, void *reserved)
{
        int res = jvm->GetEnv((void **)&jvmpi, JVMPI_VERSION_1);

        if (res < 0) {
                return JNI_ERR;
        }

        jvmpi->NotifyEvent = notify;

        JVMPI(EnableEvent)(JVMPI_EVENT_CLASS_LOAD, NULL);
        JVMPI(EnableEvent)(JVMPI_EVENT_JVM_SHUT_DOWN, NULL);
        JVMPI(EnableEvent)(JVMPI_EVENT_METHOD_ENTRY, NULL);

        return JNI_OK;
}

void
notify(JVMPI_Event * event)
{
        static jmethodID id1, id2;
        static unsigned long count1, count2;

        switch (event->event_type) {
        case JVMPI_EVENT_CLASS_LOAD:
                if (strcmp("strings", event->u.class_load.class_name) == 0) {
                        JVMPI_Method * m = event->u.class_load.methods;
                        for (jint i = 0; i < event->u.class_load.num_methods &&
(!id1 || !id2); ++i) {
                                if (strcmp(m[i].method_name, "detab_StringBuffer
") == 0) {
                                        id1 = m[i].method_id;
                                        cout << "detab_StringBuffer ID: " << (vo
id *)id1 << endl;
                                } else if (strcmp(m[i].method_name, "detab_Strin
g") == 0) {
                                        id2 = m[i].method_id;
                                        cout << "detab_String ID: " << (void *)i
d2 << endl;
                                }
                        }
                }
                break;
        case JVMPI_EVENT_JVM_SHUT_DOWN:
                cout << "detab_StringBuffer(): " << count1 << endl;
                cout << "detab_String(): " << count2 << endl;
                break;
        case JVMPI_EVENT_METHOD_ENTRY:
                if (event->u.method.method_id == id1) {
                        ++count1;
                } else if (event->u.method.method_id == id2) {
                        ++count2;
                }
                break;
        default:
                break;
        }
}