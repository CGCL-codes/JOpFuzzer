
JAVA FILE
#############################################################################
public class JNIMain {

  public native byte getBiteID (short biteID) ;

  public native short[] decode (short[] biteID) ;

  public JNIMain() {
  }
  public static void main(String[] args) {
    JNIMain JNIMain1 = new JNIMain();
    System.loadLibrary("JNIMainImp");
    System.out.println("resu :" + JNIMain1.getBiteID((short)0x1000) );


    short[] lshort = {(short) 0xFFFE,//1    0  1
                 (short) 0xFFFF,//2    2  3
                 (short) 0xC717,//3    4  5
                 (short) 0x8D11,//4    6  7
                 (short) 0x0005,//5    8  9   First LRU
                 (short) 0x0006,//6   10 11
                 (short) 0x0007,//7   12 13
                 (short) 0x0008,//8   14 15
                 (short) 0x0036,//9   16 17   2 Failed = 36  : 1 Failed = 6
                 (short) 0x40AF,//10  18 19
                 (short) 0x90BF,//11  20 21
                 (short) 0xE0CF,//12  22 23
                 (short) 0x30DF,//13  24 25
                 (short) 0xEFFF,//14  26 27
                 (short) 0xCEFB,//15  28 29
                 (short) 0x000B,//16  30 31    Total length
                 (short) 0x0006,//17  32 33    first set length
                 (short) 0x0012,//18  34 35    ID
                 (short) 0xF013,//19  36 37
                 (short) 0xF014,//20  38 39
                 (short) 0xF015,//21  40 41
                 (short) 0xF016,//22  42 43
                 (short) 0x0004,//23  44 45    second set length
                 (short) 0x0018,//24  46 47     ID
                 (short) 0xF019,//25  48 49
                 (short) 0xF01A};//26 50 51


	long start = System.currentTimeMillis();
        //short[] decodeArray ;
	short[] decodeArray  = JNIMain1.decode (lshort);

        for (int k=0;k<10000;k++) {
          System.out.print(k+" ");
          decodeArray  = JNIMain1.decode (lshort);
        }

	System.out.println("decode 1000:" + (System.currentTimeMillis()-start));
###############################################################################

C FILE
###############################################################################
#include <jni.h>
#include "JNIMain.h"
#include <stdio.h>

	struct word1 {
		short firstBit:1;
		short func:1;
		short status:2;
		short cd:1;
		short bite_id:8;
		short side:3;
		};

	struct word2 {
		short failure_class:3;
		short flight_deck_effect_code:7;
		short FDE_event:6;
		};


	struct word3 {
		short first_proba:4;
		short link12:1;
		short second_proba:4;
		short link23:1;
		short third_proba:4;
		short io:1;
		short prio:1;
		};

	struct word4 {
		short status1:1;
		short status2:1;
		short status3:1;
		short status4:1;
		short decoding_key1:3;
		short decoding_key2:3;
		short decoding_key3:3;
		short decoding_key4:3;
		};

	struct word9 {
		short first_io_status:3;
		short second_io_status:3;
		short third_io_status:3;
		short fourth_io_status:3;
		short four_zeros:4;
		};



	struct wordIO {
		short connector:4;
		short pin_code:8;
		short owner1:2;
		short owner2:2;
		};



JNIEXPORT jbyte JNICALL Java_JNIMain_getBiteID  (JNIEnv *env, jobject b, jshort
c) {
	short* toto= &c;
	struct word1* myWord = toto;
	return myWord->bite_id;

}

JNIEXPORT jshortArray JNICALL Java_JNIMain_decode  (JNIEnv *env, jobject b,
jshortArray jsa) {

	jshortArray returned;
	jshort *param =(*env)->GetIntArrayElements(env,jsa,0);
	struct word1* dw1 = &(param[0]);
	struct word2* dw2 = &(param[1]);
	struct word3* dw3 = &(param[2]);
	struct word4* dw4 = &(param[3]);
	struct word9* dw9 = &(param[8]);
	struct wordIO* dwIo1 = &(param[9]);
	struct wordIO* dwIo2 = &(param[10]);
	struct wordIO* dwIo3 = &(param[11]);
	struct wordIO* dwIo4 = &(param[12]);

	int i=0;

	jshort *arr;
	arr = (short*)malloc(sizeof(jshort)*47);


	arr[0] = dw1->firstBit;
	arr[1] = dw1->func;
	arr[2] = dw1->status;
	arr[3] = dw1->cd;
	arr[4] = dw1->bite_id;
	arr[5] = dw1->side;

	arr[6] = dw2->failure_class;
	arr[7] = dw2->flight_deck_effect_code;
	arr[8] = dw2->FDE_event;

	arr[9]  = dw3->first_proba;
	arr[10] = dw3->link12;
	arr[11] = dw3->second_proba;
	arr[12] = dw3->link23;
	arr[13] = dw3->third_proba;
	arr[14] = dw3->io;
	arr[15] = dw3->prio;

	arr[16] = dw4->status1;
	arr[17] = dw4->status2;
	arr[18] = dw4->status3;
	arr[19] = dw4->status4;
	arr[20] = dw4->decoding_key1;
	arr[21] = dw4->decoding_key2;
	arr[22] = dw4->decoding_key3;
	arr[23] = dw4->decoding_key4;

	arr[24] = param[4];
	arr[25] = param[5];
	arr[26] = param[6];
	arr[27] = param[7];

	arr[28] = dw9->first_io_status;
	arr[29] = dw9->second_io_status;
	arr[30] = dw9->third_io_status;
	arr[31] = dw9->fourth_io_status;

	arr[32] = dwIo1->connector;
	arr[33] = dwIo1->pin_code;
	arr[34] = dwIo1->owner1;
	arr[35] = dwIo1->owner2;

	arr[36] = dwIo2->connector;
	arr[37] = dwIo2->pin_code;
	arr[38] = dwIo2->owner1;
	arr[39] = dwIo2->owner2;

	arr[40] = dwIo3->connector;
	arr[41] = dwIo3->pin_code;
	arr[42] = dwIo3->owner1;
	arr[43] = dwIo3->owner2;

	arr[44] = dwIo4->connector;
	arr[45] = dwIo4->pin_code;
	arr[46] = dwIo4->owner1;
	arr[47] = dwIo4->owner2;

	returned = (*env)->NewIntArray(env,47);
	(*env)->SetIntArrayRegion(env,returned,0,47,arr);
	return returned;
}

