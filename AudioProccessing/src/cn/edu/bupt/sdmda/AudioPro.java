package cn.edu.bupt.sdmda;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import jm.audio.io.*;
import jm.util.Play;

public class AudioPro {
	
	private String audioFile=null;
	private float[] data;
	private int channel;
	private int bitDepth;
	private int sampleRate;
	private AudioInputStream ais;
	private Clip clip;
	
	public AudioPro(String filePath)
	{
		AudioFileIn afi = new AudioFileIn(filePath);
		data            = afi.getSampleData();
		channel         = afi.getChannels();
		bitDepth        = afi.getBitResolution();
		sampleRate      = afi.getSampleRate();
	}
	
	public AudioPro(float[] d,int c,int b,int s)
	{
		data       = d;
		channel    = c;
		bitDepth   = b;
		sampleRate = s;
	}

	public float[] getData() {
		return data;
	}

	public int getChannel() {
		return channel;
	}

	public int getBitDepth() {
		return bitDepth;
	}

	public int getSampleRate() {
		return sampleRate;
	}
	
	public void write()
	{
		JFileChooser fc=new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.SAVE_DIALOG|JFileChooser.DIRECTORIES_ONLY);
		fc.showDialog(null,"save");
		File f=fc.getSelectedFile();
		String filePath=f.getAbsolutePath()+"\\a.aiff";	
		this.audioFile=filePath;
		try{
			new AudioFileOut(data,filePath,channel,sampleRate,bitDepth);
		}
		catch(Exception e)
		{}
	}
	
	public void write(String filePath)
	{
		new AudioFileOut(data, filePath, channel, sampleRate, bitDepth);
	}
	
	public void write(String fileName, int channel, int sampleRate,int bitDepth) 
	{
		new AudioFileOut(data, fileName, channel, sampleRate, bitDepth);
	}
	
	public void play()
	{
		write();
		try {
			ais=AudioSystem.getAudioInputStream(new File(audioFile));
			clip=(Clip)AudioSystem.getClip();
			clip.open(ais);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pause()
	{
		
	}
	
	public void stop()
	{
		clip.stop();
	}
	
	public float[] mono(int c)
	{
		float[] ret = new float[data.length/channel];
		if(c<0 || c>channel)
		{
			return null;
		}
		for(int i=0;i<data.length;i+=channel)
		{
			ret[i/channel] = data[i];
		}
		return ret;
	}
	
	public float[] mono1earPhone(int c)
	{
		float[] ret = new float[data.length];
		if(c<0 || c>channel)
		{
			return null;
		}
		for(int i=0;i<data.length;i+=channel)
		{
			if(c==i%channel)
			{
				ret[i] = data[i];
			}else
			{
				ret[i] = 0;
			}
		}
		return ret;
	}
	
	public float[] reverse()
	{
		float[] ret = new float[data.length];
		for(int i=0;i<data.length;++i)
		{
			ret[i] = data[data.length-1-i];
		}
		return ret;
	}
	
	public float[] chop(int start,int end)
	{
		int length = end - start;
		float[] ret = new float[length];
		for(int i=start;i<end;i++)
		{
			ret[i-start] = data[i];
		}
		return ret;
	}
	
	public float[] volume(float gain)
	{
		float[] ret = new float[data.length];
		for(int i=0;i<data.length;i++)
		{
			ret[i] = data[i]*gain;
		}
		return ret;
	}
	
	public float[] normalize()
	{
		float maxV = 0;
		for(float e:data)
		{
			if(Math.abs(e)>maxV)
			{
				maxV = Math.abs(e);
			}
		}
		float scale = 1/maxV;
		return volume(scale);
	}
	
	public float[] compress(float t,float r)
	{
		float[] ret = new float[data.length];
		for(int i=0;i<data.length;i++)
		{
			if(Math.abs(data[i])<=t)
			{
				ret[i] = data[i];
			}else
			{
				if(data[i]>0)
				{
					ret[i] = t + (data[i]-t)*r;
				}else
				{
					ret[i] = -t + (data[i]-t)*r;
				}
			}
		}
		return ret;
	}
	
	public float[] fading(int start,int end)
	{
		float[] ret = data.clone();
		int length = end - start;
		for(int i=start;i<end;i++)
		{
			ret[i] *= (end-i)/(float)length;
		}
		return ret;
	}
	
	public float[] mix(float[] d,float sourceV,float mixV)
	{
		float[] longer,shorter;
		if(d.length>data.length)
		{
			longer  = d;
			shorter = data;
		}else
		{
			longer  = data;
			shorter = d;
		}
		float[] ret = new float[longer.length];
		for(int i=0;i<shorter.length;i++)
		{
			ret[i] = sourceV*longer[i] + mixV*shorter[i];
		}
		return ret;
	}
	
	public float[] resample(float rate,boolean linear)
	{
		if(1==rate)
		{
			return data;
		}
		float[] ret = new float[(int)(Math.ceil((data.length-1)*rate)+1)];
		ret[0] = data[0];
		for(int i=1;i<data.length;i++)
		{
			int newLoc = (int) Math.ceil((i*rate));
			int start  = (int) Math.ceil((i-1)*rate)+1;
			for(int j=start;j<newLoc;++j)
			{
				if(linear)
				{ret[j] = (data[i]*(newLoc-j)+data[i-1]*(j-start))/(newLoc-start);}
				else
				{ret[j] = 0;}
			}
		}
		return ret;
	}
	
	public float[] resample(float rate)
	{
		if(1==rate)
		{
			return data;
		}
		float[] ret = new float[(int)(Math.ceil((data.length-1)*rate)+1)];
		ret[0] = data[0];
		for(int i=1;i<data.length;i++)
		{
			int newLoc = (int) Math.ceil((i*rate));
			int start  = (int) Math.ceil((i-1)*rate)+1;
			for(int j=start;j<newLoc;++j)
			{
			   ret[j] = (data[i]*(newLoc-j)+data[i-1]*(j-start))/(newLoc-start);
			}
			ret[newLoc]=data[i];
		}
		normalize();
		return ret;
	}
	
	public static float[] hanning(int size)
	{
		float[] ret = new float[size];
		for(int i=0;i<size;++i)
		{
			ret[i] = (float)(0.5-0.5*Math.cos(2*Math.PI*i/size));
		}
		return ret;
	}
	
	public float[] ola(float rate)
	{
		int wndSz   = sampleRate/10;
		int anaStep = wndSz/2;
		int synStep = (int)(anaStep*rate);
		int numWnd  = (int) Math.ceil((data.length-wndSz)/anaStep+1);
		float[] hanningWnd = hanning(wndSz);
		float[][] frames = new float[numWnd][];
		for(int i=0;i<numWnd;++i)
		{
			frames[i] = new float[wndSz];
		}
		for(int i=0;i<numWnd;++i)
		{
			for(int j=0;j<wndSz;++j)
			{
				try{
					frames[i][j] = data[i*anaStep+j]*hanningWnd[j];
				}catch (ArrayIndexOutOfBoundsException e){
					frames[i][j] = 0;
				}
			}
		}
		
		int N = wndSz + (numWnd-1)*synStep;
		float[] ret = new float[N];
		for(int i=0;i<N;++i)
		{
			ret[i] = 0;
		}
		for(int i=0;i<numWnd;++i)
		{
			for(int j=0;j<wndSz;++j)
			{
				ret[i*synStep+j] += frames[i][j];
			}
		}
		return ret;
	}
	
	public float[][] getFrames(int frameSize,int overlap)
	{
		int step = frameSize - overlap;
		int nFrames = (int)Math.ceil(
				(data.length - frameSize)/(float)step +1);
		
		float[][] ret = new float[nFrames][];
		for(int i=0;i<nFrames;i++)
		{
			ret[i] = new float[frameSize];
		}
		
		float[] wnd = hanning(frameSize);
		for(int i=0;i<nFrames;i++)
		{
			for(int j=0;j<frameSize;j++)
			{try{
				ret[i][j]=data[i*step+j]*wnd[j];
			}catch(ArrayIndexOutOfBoundsException e){
							ret[i][j] = 0;
						}
			}
			
		}
		return ret;
	}
	
	public float[] getVol1(int frameSize, int overlap)
	{
		float[][] frames = getFrames(frameSize, overlap);
		float[] ret = new float[frames.length];
		
		for(int i=0;i<frames.length;i++)
		{
			float sum = 0;
			for(int j=0;j<frames[i].length;j++)
			{
				sum+= Math.abs(frames[i][j]);
			}
			ret[i] = sum;
		}
		return ret;
	}
	
	public float[] getVol2(int frameSize, int overlap)
	{
		float[][] frames = getFrames(frameSize, overlap);
		float[] ret = new float[frames.length];
		
		for(int i=0;i<frames.length;i++)
		{
			float sum = 0;
			for(int j=0;j<frames[i].length;j++)
			{
				sum+= frames[i][j]*frames[i][j];
			}
			ret[i] = (float)(10*Math.log10(sum));
		}
		return ret;
	}
	
	public static void plot(float[] data,String chartTitle,
			String XTitle,String YTitle,String seriesTitle)
	{

		double[] time = new double[data.length];
		double[] v = new double[data.length];
		for(int i=0;i<data.length;i++)
		{
			time[i] = i+1;
			v[i] = data[i];
		}

		
		XYChart chart = QuickChart.getChart(chartTitle, XTitle, YTitle, seriesTitle, time, v);
		
		new SwingWrapper(chart).displayChart();
	}
	
	public float[] getZCR(int frameSize,int overlap)
	{
		float[][] frames = getFrames(frameSize,overlap);
		float[] ret = new float[frames.length];
		
		for(int i=0;i<frames.length;i++)
		{
			int sum =0;
			for(int j=0;j<frames[i].length-1;j++)
			{
				if(frames[i][j]*frames[i][j+1] <= 0)
				{sum++;}
			}
			ret[i] = sum;
		}
		return ret;
	}
	
	public ArrayList<int[]> EPD(float hvt,float lvt,float zt,int frameSize,int overlap)
	{
		float[] vol = getVol2(frameSize,overlap);
		float[] zcr = getZCR(frameSize,overlap);
		
		ArrayList<int[]> endPoint = new ArrayList<int[]>();
		boolean previous = false;
		
		//step 1
		for(int i=0;i<vol.length;i++)
		{
			boolean current = vol[i]>hvt?true:false;
			if(current && !previous)
			{
				int[] temp = new int[2];
				temp[0] = i;
				temp[1] = i;
				endPoint.add(temp);
			}
			if(current && previous)
			{
				int[] temp = endPoint.get(endPoint.size()-1);
				temp[1] += 1;
			}
			if(current && previous)
			{
				
			}
			if(current && !previous)
			{
				
			}
			previous = current;
		}
		
		//step 2
		ArrayList<int[]> NendPoint = new ArrayList<int[]>();
		for(int[] e:endPoint)
		{
			if(NendPoint.size()>0)
			{
				int[] temp = NendPoint.get(NendPoint.size()-1);
				if(e[1]<=temp[1]){continue;}
			}
			
			//left
			int left = e[0];
			while(vol[left]>=lvt&&zcr[left]<=zt&&left>=0)
			{
				left--;
			}
			//right
			int right = e[1];
			while(vol[right]>=lvt&&zcr[right]<=zt&&right<vol.length)
			{
				right++;
			}
			//
			int[] temp = new int[2];
			temp[0] = left;
			temp[1] = right;
			NendPoint.add(temp);
			
		}
			return NendPoint;
		
	}
	
//	public float[] EPD(float hvt,float lvt,float zt,int frameSize,int overlap)
//	{
//		float[] vol = getVol2(frameSize,overlap);
//		float[] zcr = getZCR(frameSize,overlap);
//		
//		ArrayList<int[]> endPoint = new ArrayList<int[]>();
//		boolean previous = false;
//		
//		//step 1
//		for(int i=0;i<vol.length;i++)
//		{
//			boolean current = vol[i]>hvt?true:false;
//			if(current && !previous)
//			{
//				int[] temp = new int[2];
//				temp[0] = i;
//				temp[1] = i;
//				endPoint.add(temp);
//			}
//			if(current && previous)
//			{
//				int[] temp = endPoint.get(endPoint.size()-1);
//				temp[1] += 1;
//			}
//			if(current && previous)
//			{
//				
//			}
//			if(current && !previous)
//			{
//				
//			}
//			previous = current;
//		}
//		
//		//step 2
//		ArrayList<int[]> NendPoint = new ArrayList<int[]>();
//		for(int[] e:endPoint)
//		{
//			if(NendPoint.size()>0)
//			{
//				int[] temp = NendPoint.get(NendPoint.size()-1);
//				if(e[1]<=temp[1]){continue;}
//			}
//			
//			//left
//			int left = e[0];
//			while(vol[left]>=lvt&&zcr[left]<=zt&&left>=0)
//			{
//				left--;
//			}
//			//right
//			int right = e[1];
//			while(vol[right]>=lvt&&zcr[right]<=zt&&right<vol.length)
//			{
//				right++;
//			}
//			//
//			int[] temp = new int[2];
//			temp[0] = left;
//			temp[1] = right;
//			NendPoint.add(temp);
//			
//		}
//		
////		float[] ret = new float[NendPoint.indexOf(1) - NendPoint.indexOf(0)];
//		chop(NendPoint.indexOf(0),NendPoint.indexOf(1));
//			return NendPoint;
//		
//	}
	
	
	
	public float[] filter(float[] b,float[] a)
	{
		LinkedList<Float> qa = new LinkedList<Float>();
		LinkedList<Float> qb = new LinkedList<Float>();
		HashMap<Integer, Float> ha = new HashMap<Integer, Float>();
		HashMap<Integer, Float> hb = new HashMap<Integer, Float>();
		for(int i=1;i<b.length;i++)
		{
			qb.add(0f);
			if(b[i]!=0)
				hb.put(i, b[i]);
		}
		for(int i=1;i<a.length;i++)
		{
			qa.add(0f);
			if(a[i]!=0)
				ha.put(i, a[i]);
		}
		float[] y = new float[data.length];
		for(int i=0;i<data.length;i++)
		{
			float cur = data[i];
			float res = cur*b[0];
			for(Integer k:hb.keySet())
			{
				res += qb.get(b.length-k-1)*hb.get(k);
			}
			for(Integer k:ha.keySet())
			{
				res-=qa.get(b.length-k-1)*ha.get(k);
			}
			qb.add(cur);
			qb.remove(0);
			qa.add(res);
			qa.remove(0);
			y[i]=res;
		}
		return y;
	}
	
	
	
	
}
