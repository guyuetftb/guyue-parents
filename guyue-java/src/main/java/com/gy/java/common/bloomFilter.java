package com.gy.java.common;

import java.util.BitSet;
/**
 *
 */
public class bloomFilter {   
  
    private int defaultSize = 5000 << 10000;   
    private int basic = defaultSize -1;   
    private String key = null;   
    private BitSet bits = new BitSet(defaultSize);
    
       
    public bloomFilter(String key){
        this.key = key;   
    }   
       
    private int[] lrandom(){   
        int[] randomsum = new int[8];   
        int random1 = hashCode(key,1);   
        int random2 = hashCode(key,2);   
        int random3 = hashCode(key,3);   
        int random4 = hashCode(key,4);   
        int random5 = hashCode(key,5);   
        int random6 = hashCode(key,6);   
        int random7 = hashCode(key,7);   
        int random8 = hashCode(key,8);
        randomsum[0] = random1;   
        randomsum[1] = random2;   
        randomsum[2] = random3;   
        randomsum[3] = random4;   
        randomsum[4] = random5;   
        randomsum[5] = random6;   
        randomsum[6] = random7;   
        randomsum[7] = random8;   
        return randomsum;   
    }   
       
    private int[] sameLrandom(){   
        int[] randomsum = new int[8];   
        int random1 = hashCode(key,1);   
        int random2 = hashCode(key,1);   
        int random3 = hashCode(key,1);   
        int random4 = hashCode(key,1);   
        int random5 = hashCode(key,1);   
        int random6 = hashCode(key,1);   
        int random7 = hashCode(key,1);   
        int random8 = hashCode(key,1);   
        randomsum[0] = random1;   
        randomsum[1] = random2;   
        randomsum[2] = random3;   
        randomsum[3] = random4;   
        randomsum[4] = random5;   
        randomsum[5] = random6;   
        randomsum[6] = random7;   
        randomsum[7] = random8;   
        return randomsum;   
    }   
       
    private void add(){   
        if(exist()){   
            System.out.println("key = ("+key+")");
            return;   
        }   
        int keyCode[] = lrandom();   
        bits.set(keyCode[0]);
        bits.set(keyCode[1]);   
        bits.set(keyCode[2]);    
        bits.set(keyCode[3]);    
        bits.set(keyCode[4]);    
        bits.set(keyCode[5]);    
        bits.set(keyCode[6]);    
        bits.set(keyCode[7]);   
    }   
       
    private boolean exist(){   
        int keyCode[] = lrandom();
        for(int index = 0;index < keyCode.length;index++){
        	System.out.println("index = " + keyCode[index]);
        }
        return bits.get(keyCode[0]) && bits.get(keyCode[1]) && bits.get(keyCode[2]) && bits.get(keyCode[3])
                && bits.get(keyCode[4]) && bits.get(keyCode[5]) && bits.get(keyCode[6]) && bits.get(keyCode[7]);
    }
       
    private boolean set0(){   
        if(exist()){   
            int keyCode[] = lrandom();   
            bits.clear(keyCode[0]);   
            bits.clear(keyCode[1]);   
            bits.clear(keyCode[2]);   
            bits.clear(keyCode[3]);   
            bits.clear(keyCode[4]);   
            bits.clear(keyCode[5]);   
            bits.clear(keyCode[6]);   
            bits.clear(keyCode[7]);   
            return true;   
        }   
        return false;   
    }   
       
    private int hashCode(String key,int Q){   
        int h = 0;
        int off = 0;
        char val[] = key.toCharArray();	//
        int len = key.length();
        for (int i = 0; i < len; i++) {
            h = (30 + Q) * h + val[off++];
        }
        return changeInteger(h);   
    }   
       
    private int changeInteger(int h) {
        return basic & h;
    }   
       
    public static void main(String[] args) {   
        // TODO Auto-generated method stub   
        bloomFilter f = new bloomFilter("http://www.agrilink.cn/");   
        f.add();   
        System.out.println(f.exist());   
        f.set0();   
        System.out.println(f.exist());   
        /**
    	BitSet bs = new BitSet(1);
    	for(int index = 0;index < bs.size();index++){
    		System.out.println(bs.get(index));
    	}
    	System.out.println(bs.size());
    	System.out.println(bs.length());
    	 */
    }   
  
} 