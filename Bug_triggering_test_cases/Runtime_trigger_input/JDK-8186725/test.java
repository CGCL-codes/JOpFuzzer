
JAVA source code:

package com.all.sndandlgjni;

import static com.all.sndsepa.com.SndSepUtils.*;

public class SndanDlgCpp {
	
	private long sliceLen;
	
  static {   	
  	try {
	   	//Path source = new File(wrkDir + "..\\soundAn\\Jars\\WaveRecorderToJava.dll").toPath();
	   	//Path target = new File("C:\\ProgramData\\Oracle\\Java\\javapath\\WaveRecorderToJava.dll").toPath();  	
	  	//Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
	  	//System.loadLibrary("WaveRecorderToJava");	
  		
	  	System.load(wrkDir + "\\..\\soundAn\\Jars\\WaveRecorderToJava.dll");
  	} catch(Exception sec) {
  		sec.printStackTrace();
  	}  	
  }

  SndanDlgCpp(long auLen)  {
  	sliceLen = auLen;
  }
  
  public native void initCppSide(int audioLen);
  
  //public void addWavSzelet(byte[] wavSzelet) {
  public static void test(int vmi) {
  	printLog("test - arrived any sound slice in length is: " + vmi);
  }

  
  //public void addWavSzelet(byte[] wavSzelet) {
  public void addWavSzelet() {
  	
  	//printLog("addWavSzelet - arrived any sound slice in length " +  wavSzelethosz + " byte!");
  	printLog("addWavSzelet - arrived any sound slice in length ");
  	
  } //addWavSlice()

} // class SndanDlgCpp



************************************************************************
VSC++ source code:
// WaveRecorderToStream.cpp : Defines the entry point for the DLL.
//

#include <Windows.h>
#include <stdlib.h>
#include <mmsystem.h>
#include <cstdlib>
#include <commdlg.h>
#include <stdio.h>
#include <string>
#include <iostream>
#include <fstream>
#include <sys/types.h>
#include <sys/timeb.h>

#include<jni.h>
#include "com_all_sndandlgjni_SndanDlgCpp.h"

#pragma comment(lib, "winmm.lib")

using namespace std;

MMCKINFO ckRIFF, ckFMT, ckDATA;
//wchar_t outF1[70], outFp[70] = L"./";
wchar_t outF1[370], outFp[370] = L"./";

struct _timeb timebuffer;

HWAVEIN hWaveIn;
MMRESULT result;
WAVEHDR WaveInHdr1, WaveInHdr2, *WaveInHdr;
boolean fordito = 1;
wchar_t s[82];
//char p[15], q[5], hx[5];
int fileIdx = 1;
short *waveIn1, *waveIn2;
WAVEFORMATEX pFormat;

//for java communications
JNIEnv *currentJVMenv;
jobject currJavaObj;
DWORD sliceLen = 5;
jmethodID currMethID;
jclass currClas;

//void SaveWAVFile(LPWSTR lpszPathName, HPSTR pSound, LONG cbSound, WAVEFORMATEX pWfx, LONG cbWfx);

void CALLBACK waveInProc(HWAVEIN hWaveIn, UINT message, DWORD dwInstance, DWORD wParam, DWORD lParam);

//tcpipClient tcpipc;


//int main() { //for DLL
//int main(int argc, char *argv[], char *envp[]) {
int wmain(int argc, wchar_t *argv[], wchar_t *envp[]) {

  /*JavaVM *jvm = new JavaVM();       // denotes a Java VM 
  JNIEnv *env = new JNIEnv();       // pointer to native method interface 
  JavaVMInitArgs vm_args; // JDK/JRE 6 VM initialization arguments 
  JavaVMOption options[3];

  options[0].optionString = "-Djava.compiler=NONE";           // disable JIT 
  options[0].extraInfo = nullptr;
  //options[1].optionString = "-Djava.class.path=I:\\kgy\\CNTAN\\gyozo_sndan_dialog_jni\\Release\\soundAn\\Jars\\sndan_dlg_jni.jar"; // user classes 
  options[1].optionString = "-Djava.class.path=I:/kgy/CNTAN/gyozo_sndan_dialog_jni/Release/soundAn/Jars/sndan_dlg_jni.jar;I:/kgy/CNTAN/gyozo_sndan_dialog_jni/Release/ASR/Jars/m_asr_2.jar;I:/kgy/CNTAN/gyozo_sndan_dialog_jni/Release/ASR/Jars/meet2txt_asr.jar"; // user classes 
  options[1].extraInfo = nullptr;
  //options[2].optionString = "-Djava.library.path=\\usr\\lib\\java";  // set native library path 
  options[2].optionString = "-verbose:jni";                   // print JNI-related messages 
  options[2].extraInfo = nullptr;

  vm_args.version = JNI_VERSION_1_8;
  vm_args.nOptions = 3;
  vm_args.options = options;
  vm_args.ignoreUnrecognized = JNI_TRUE; // remove unrecognized options

  // load and initialize a Java VM, return a JNI interface pointer in env 
  int ret = JNI_CreateJavaVM(&jvm, (void**)&env, &vm_args);
  // invoke the Main.test method using the JNI 
  jclass clsa = env->FindClass("com/all/sndandlgjni/SndanDlgCpp");
  jclass clsb = env->FindClass("com/all/sndandlgjni/SndanDlgApp");
  jclass clsc = env->FindClass("com/all/sndandlgjni/SndanDlg");
  jclass clsd = env->FindClass("com/all/sndandlgjni/Kivetito");
  jmethodID mid = env->GetStaticMethodID(clsa, "test", "(I)V");
  env->CallStaticVoidMethod(clsa, mid, 100);
  // We are done. 
  jvm->DestroyJavaVM();
  delete options; */




  //DWORD sliceLen = 5;

  /*if(argc == 2) { sliceLen = _wtoi(argv[1]); }
  if(argc == 3) {
    sliceLen = _wtoi(argv[1]);
    fileIdx = _wtoi(argv[2]);
  }
  if (argc < 4) {
    wcerr << "WavRec - WaveRecorderToStream <length in sec> <start file index> <path toresult file> !" << endl;
  }	else {
    sliceLen = _wtoi(argv[1]);
    fileIdx = _wtoi(argv[2]);
    wcscpy_s(outFp, argv[3]);
  }*/
  
  /*int ret = tcpipc.init_tcpipClient(); //set the server
  if(ret == 0) { ret = tcpipc.start_tcpipClient(); } //connect to server
  if(ret != 0) {
    wcerr << "WavRec - We can't connects to server" << endl;
    return 1957;
  }*/ 

wcerr << L"WaveRecorderToJava - in wmain(0)!" << endl;

  // Creating 'RIFF' and 'WAVE' chunks
	ckRIFF.fccType = mmioFOURCC('W', 'A', 'V', 'E');
	ckRIFF.cksize = 0L;
	ckRIFF.dwFlags = MMIO_DIRTY;

	// Creating 'fmt ' chunk
	ckFMT.ckid = mmioFOURCC('f', 'm', 't', ' ');
	ckFMT.cksize = 0L;
	ckFMT.dwFlags = MMIO_DIRTY;

	// Creating 'data' chunk
	ckDATA.ckid = mmioFOURCC('d', 'a', 't', 'a');
	ckDATA.cksize = 0L;
	ckDATA.dwFlags = MMIO_DIRTY;

	int sampleRate = 16000;
	const int NUMPTS = sampleRate * sliceLen;
	waveIn1 = new short[NUMPTS];
	waveIn2 = new short[NUMPTS];

	pFormat.wFormatTag = WAVE_FORMAT_PCM;
	pFormat.nChannels = 1;
	pFormat.nSamplesPerSec = sampleRate;
	pFormat.nAvgBytesPerSec = 2 * sampleRate;
	pFormat.nBlockAlign = 2;
	pFormat.wBitsPerSample = 16;
	pFormat.cbSize = 0;

wcerr << L"WaveRecorderToJava - in wmain(1)!" << endl;
  
  result = waveInOpen(&hWaveIn, WAVE_MAPPER, &pFormat, (DWORD)&waveInProc, 0, WAVE_FORMAT_DIRECT + CALLBACK_FUNCTION);

  
wcerr << L"WaveRecorderToJava - in wmain(2)!" << endl;
  
  if(result) {
wcerr << L"WaveRecorderToJava - in wmain(3)!" << endl;
    char fault[256];
		waveInGetErrorTextA(result, fault, 256);
		wcerr << "WavRec - Failed to open the sound adapter : " << fault << endl;
		return 1;
	}

wcerr << L"WaveRecorderToJava - in wmain(4)!" << endl;

	WaveInHdr1.lpData = (LPSTR)waveIn1;
	WaveInHdr1.dwBufferLength = 2 * NUMPTS;
	WaveInHdr1.dwBytesRecorded = 0;
	WaveInHdr1.dwUser = 0;
	WaveInHdr1.dwFlags = 0;
	WaveInHdr1.dwLoops = 0;
	waveInPrepareHeader(hWaveIn, &WaveInHdr1, sizeof(WAVEHDR));

wcerr << L"WaveRecorderToJava - in wmain(5)!" << endl;


	WaveInHdr2.lpData = (LPSTR)waveIn2;
	WaveInHdr2.dwBufferLength = 2 * NUMPTS;
	WaveInHdr2.dwBytesRecorded = 0;
	WaveInHdr2.dwUser = 0;
	WaveInHdr2.dwFlags = 0;
	WaveInHdr2.dwLoops = 0;
	waveInPrepareHeader(hWaveIn, &WaveInHdr2, sizeof(WAVEHDR));	
	
wcerr << L"WaveRecorderToJava - in wmain(6)!" << endl;

	//recording of 1st slice
	result = waveInAddBuffer(hWaveIn, &WaveInHdr1, sizeof(WAVEHDR));
	result = waveInStart(hWaveIn);
	if(result) { wcerr << L"WavRec - Failed to start recording!" << endl; return 1; }

wcerr << L"WaveRecorderToJava - in wmain(7)!" << endl;

	_ftime_s(&timebuffer);
	swprintf_s(s, (size_t)82, L"WavRec - %d. record start time stamp : %I64d sec, %hu millisec", fileIdx, timebuffer.time, timebuffer.millitm);
	wcerr << s << endl;

wcerr << L"WaveRecorderToJava - in wmain(8)!" << endl;

	while(true) { Sleep(sampleRate); }

	waveInUnprepareHeader(hWaveIn, &WaveInHdr1, sizeof(WAVEHDR));
	waveInUnprepareHeader(hWaveIn, &WaveInHdr2, sizeof(WAVEHDR));
	waveInClose(hWaveIn);
	delete waveIn1;
	delete waveIn2;

	return 0;
} // wmain()

/*****************************************
 * lpszPathName - Pathname of file
 * pSound - Pointer to sound buffer
 * cbSound - size in bytes of sound buffer
 * pWfx - pointer to WAVEFORMATEX structure
 * cbWfx - size of this structure
 */
void SaveWAVFile(LPWSTR lpszPathName, HPSTR pSound, LONG cbSound, WAVEFORMATEX pWfx, LONG cbWfx)
{
	MMIOINFO  mmioinfo;
	HMMIO hmmio;

	::ZeroMemory(&mmioinfo, sizeof(mmioinfo));

	hmmio = mmioOpen((LPWSTR)lpszPathName, &mmioinfo, MMIO_WRITE | MMIO_CREATE);

	if (hmmio == NULL) { wcerr << L"WavRec - mmioOpen returns NULL (" << (lpszPathName) << L")" << endl; }

	// Creating 'RIFF' and 'WAVE' chunks
	mmioCreateChunk(hmmio, &ckRIFF, MMIO_CREATERIFF);

	// Creating 'fmt ' chunk
	mmioCreateChunk(hmmio, &ckFMT, 0);
	mmioWrite(hmmio, (HPSTR)&pWfx, cbWfx);

	// Goto to 'WAVE' chunk and update 'fmt ' chunk size
	mmioAscend(hmmio, &ckFMT, 0);

	mmioCreateChunk(hmmio, &ckDATA, 0);
	mmioWrite(hmmio, (HPSTR)pSound, cbSound);

	// Goto to 'WAVE' chunk and update 'data' chunk size
	mmioAscend(hmmio, &ckDATA, 0);

	// Goto to 'RIFF' chunk and update 'WAVE' chunk size
	mmioAscend(hmmio, &ckRIFF, 0);
	mmioClose(hmmio, 0);
} //SaveWAVFile()

void CALLBACK waveInProc(HWAVEIN hWaveIn, UINT message, DWORD dwInstance, DWORD wParam, DWORD lParam) {
	if(message == WIM_DATA) {
wcerr << L"WaveRecorderToJava - in waveInProc(0)!" << endl;

    //int csak_teszt;
    //byte b, d;
		_ftime_s(&timebuffer);
		swprintf_s(s, (size_t)82, L"WavRec - %d. record end time stamp : %I64d sec, %hu millisec", fileIdx++, timebuffer.time, timebuffer.millitm);
    wcerr << s << endl;
    
wcerr << L"WaveRecorderToJava - in waveInProc(1)!" << endl;

		if(fordito) { 
			result = waveInAddBuffer(hWaveIn, &WaveInHdr2, sizeof(WAVEHDR));
			result = waveInStart(hWaveIn);
			WaveInHdr = &WaveInHdr1;
		} else {
			result = waveInAddBuffer(hWaveIn, &WaveInHdr1, sizeof(WAVEHDR));
			result = waveInStart(hWaveIn);
			WaveInHdr = &WaveInHdr2;
		}
		if (result) { wcerr << L"WavRec - Failed to start recording!" << endl; return; }

wcerr << L"WaveRecorderToJava - in waveInProc(2)!" << endl;


		fordito = !fordito;

		_ftime_s(&timebuffer);
		swprintf_s(s, (size_t)82, L"WavRec - %d. record start time stamp : %I64d sec, %hu millisec", fileIdx, timebuffer.time, timebuffer.millitm);
		wcerr << s << endl;

wcerr << L"WaveRecorderToJava - in waveInProc(3)!" << endl;

		if (WaveInHdr->dwBytesRecorded > 0) {
      //send name of made sound file as message to the investigation
      swprintf_s(s, (size_t)82, L"Readed sound slice in byte length:%lu", WaveInHdr->dwBytesRecorded);
      //swprintf_s(s, (size_t)82, L"OnlineInputStream length:%lu", 32*1000);
      wcerr << s << endl;

wcerr << L"WaveRecorderToJava - in waveInProc(4a)! (currentJVMenv != null): " << (currentJVMenv != NULL) << endl;
wcerr << L"WaveRecorderToJava - in waveInProc(4b)! (currJavaObj != null): " << (currJavaObj != NULL) << endl;
wcerr << L"WaveRecorderToJava - in waveInProc(4c)! (currMethID != null): " << (currMethID != NULL) << endl;

      //currentJVMenv->CallVoidMethod(currJavaObj, currMethID, WaveInHdr->lpData);
      try {
        wcerr << L"WaveRecorderToJava - in waveInProc(4d) in try{}!" << endl;
        currentJVMenv->CallStaticVoidMethod(currClas, currMethID, 4);
      } catch(exception *e) {
        wcerr << L"WaveRecorderToJava - in waveInProc(4e) Exception in try{}!" << endl;
        string excm(e->what());
        wcerr <<  L"Exception: " + wstring(excm.begin(), excm.end()) << endl;
      }

wcerr << L"WaveRecorderToJava - in waveInProc(5)!" << endl;

 
      WaveInHdr->dwBytesRecorded = 0;
		}
	}
} //waveInProc()


JNIEXPORT void JNICALL Java_com_all_sndandlgjni_SndanDlgCpp_initCppSide(JNIEnv *cJVM, jobject jObj, jint auLn) {
  currentJVMenv = cJVM;
  currJavaObj = jObj;
  sliceLen = auLn;

  /*/readed sound sending to the java side
  JNI megvalosítas
  http://www.developer.com/java/data/getting-started-with-jni.html
  http://docs.oracle.com/javase/6/docs/technotes/guides/jni/spec/invocation.html#wp9502
  http://docs.oracle.com/javase/7/docs/technotes/guides/jni/spec/functions.html

  https://stackoverflow.com/questions/1086596/how-to-access-arrays-within-an-object-with-jni
  */
  currClas = currentJVMenv->GetObjectClass(currJavaObj);
  //currMethID = currentJVMenv->GetMethodID(currClas, "addWavSzelet", "([B)V");
  //currMethID = currentJVMenv->GetMethodID(currClas, "addWavSzelet", "()V");
  currMethID = currentJVMenv->GetStaticMethodID(currClas, "test", "(I)V");

  wcerr << L"WaveRecorderToJava - in Java_com_all_sndandlgjni_SndanDlgCpp_initCppSide! (currMethID != null): " << (currMethID != NULL) << endl;

  wmain(0, nullptr, nullptr);
 
}

