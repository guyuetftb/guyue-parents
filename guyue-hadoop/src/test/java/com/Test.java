package com;

import org.apache.hadoop.security.ShellBasedUnixGroupsMapping;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Unit test for simple App.
 */
public class Test {

    static Logger logger = Logger.getLogger(Test.class);

    public static void main(String[] args) throws Exception {
        boolean isSkip = false;
        ReentrantLock e = new ReentrantLock();

        HashSet set = null;
        ConcurrentHashMap c1 = null;
        System.out.println(c1);
        ArrayList list;

        CountDownLatch c = new CountDownLatch(1);
        File file = new File("/Users/lipeng/workspace/20151107_en_US");
        BufferedReader br = new BufferedReader(new FileReader(file));
        ShellBasedUnixGroupsMapping a = null;
        String line = null;
        while (null != (line = br.readLine())) {
            String row = line.toString();
            String[] sectionArr = row.split("\t");
            if (sectionArr.length < 2) {
                continue;
            }
            // 01c992c98bac86fca3091c22a4b06514_4.6.7_20151107	01c992c98bac86fca3091c22a4b06514	I'm trying to be more like you. Lol 	1446927044775;input:0,1033,119,I;input:234,1154,559,m;input:426,623,772, ;input:662,691,123,t;input:897,476,64,r;input:1093,811,152,y;input:1241,1045,100,i;input:1408,963,553,n;input:1494,712,356,g;input:1614,928,756, ;input:1733,660,98,t;input:1884,1200,115,o;input:1967,644,804, ;input:2061,867,356,h;input:2233,296,127,e;input:2318,1260,385,l;input:2532,1311,106,p;input:2682,645,775, ;input:2777,1196,105,o;input:3215,639,72,t;input:3428,866,304,h;input:3593,357,124,e;input:3770,452,106,r;input:3911,906,789, ;input:4112,656,121,t;input:4296,861,353,h;input:4491,1075,135,i;input:4632,277,316,s;input:4774,886,754, ;input:5035,793,170,y;input:5132,290,157,e;input:5360,84,305,a;input:5457,542,140,r;input:5618,533,754, ;input:5830,570,371,f;input:5980,1233,111,o;input:6125,483,123,r;input:6226,973,755, ;input:6366,572,638,c;input:6490,852,383,h;input:6643,507,167,r;input:6786,1099,85,i;input:6990,260,399,s;input:7042,660,175,t;input:7269,1135,594,m;input:7458,82,305,a;input:7624,236,422,s;input:7681,927,756, ;input:7800,934,749, ;input:10262,1355,517,delete;input:10312,1355,517,delete;input:10362,1355,517,delete;input:10411,1355,517,delete;input:10493,1355,517,delete;input:10543,1355,517,delete;input:11245,1349,526,delete;input:11296,1349,526,delete;input:11408,1349,526,delete;input:11459,1349,526,delete;input:11559,1349,526,delete;input:11609,1349,526,delete;input:11658,1349,526,delete;input:11725,1349,526,delete;input:11775,1349,526,delete;input:11825,1349,526,delete;input:11897,1349,526,delete;input:11948,1349,526,delete;input:12036,1349,526,delete;input:12085,1349,526,delete;input:12136,1349,526,delete;input:12233,1349,526,delete;input:12283,1349,526,delete;input:12371,1349,526,delete;input:12421,1349,526,delete;input:12485,1349,526,delete;input:12536,1349,526,delete;input:12585,1349,526,delete;input:12651,1349,526,delete;input:12701,1349,526,delete;input:12805,1349,526,delete;input:12855,1349,526,delete;input:13029,1324,527,delete;input:13187,1327,524,delete;input:13337,1314,516,delete;input:13832,903,525,b;input:14005,349,117,e;input:14057,970,746, ;input:14235,1165,573,m;input:14456,1219,144,o;input:14601,484,112,r;input:14747,319,86,e;input:14890,964,737, ;input:15042,1285,278,l;input:15269,1088,81,i;input:15450,1124,271,k;input:15589,373,118,e;input:15686,979,778, ;input:15936,788,163,y;input:16128,1255,100,o;input:16307,948,117,u;input:16592,694,755, ;input:16753,702,738, ;input:16903,1270,331,L;input:17098,1118,106,i;input:17268,1265,219,o;input:17557,658,771, ;input:18024,1317,513,delete;input:18188,1297,545,delete;input:18345,1261,570,delete;input:18602,1212,105,o;input:18783,1246,363,l;input:18859,636,727, ;
            // input:19239,49,724,symbol;
            // input:20111,912,556,;;
            // input:20388,1294,342,);

            // input track
            // 分子=1
            // 分母=-1
            // 2者=0
            String[] inputTrackArr = sectionArr[3].split(";");
            for (int index = 1; index < inputTrackArr.length; index++) {
                // initialize value container
                if ("".equals(inputTrackArr[index])) {
                    // System.out.println("xxxxxxxxxx \t[" + inputTrackArr[index] + "]");
                    continue;
                }
                String[] trackArr = inputTrackArr[index].split(",");
                if (trackArr.length == 3) {
                    if (inputTrackArr[index].charAt(inputTrackArr[index].length() - 1) == ',') {
                        if (isSkip) {
//                            System.out.println(11);
                        } else {
//                            System.out.println(22222);
                        }
                        isSkip = Boolean.TRUE;
                    } else {
                        System.out.println("yyyyy \t {" + inputTrackArr[index] + "}");
                        continue;
                    }
                } else {
                    String[] track0Arr = trackArr[0].split(":");
                    if (("choose".equals(track0Arr[0]) && "1".equals(trackArr[3])) || "\n".equals(trackArr[3]) || ".".equals(trackArr[3]) || ",".equals(trackArr[3]) || " ".equals(trackArr[3])) {
                        if (isSkip) {
//                                System.out.println(3333333);
                        } else {
//                                System.out.println(4444444);
                        }
                        isSkip = Boolean.TRUE;
                    } else {
//                            System.out.println(55555555);
                        isSkip = Boolean.FALSE;// 其他项,分母+1 [分母=-1]
                    }
                }
            }
        }
    }
}