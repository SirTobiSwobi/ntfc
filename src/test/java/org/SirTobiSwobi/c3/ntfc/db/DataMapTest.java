package org.SirTobiSwobi.c3.ntfc.db;

import static org.junit.Assert.*;

import org.SirTobiSwobi.c3.ntfc.db.DataMap;
import org.junit.Test;

public class DataMapTest {

	@Test
	public void test() {
		
		DataMap<String> map = new DataMap<String>();
		map.setContent("44", 44);
		map.setContent("17", 17);
		map.setContent("88", 88);
		map.setContent("32", 32);
		map.setContent("65", 65);
		map.setContent("97", 97);
		map.setContent("28", 28);
		map.setContent("54", 54);
		map.setContent("82", 82);
		map.setContent("29", 29);
		map.setContent("76", 76);
		map.setContent("80", 80);
		map.setContent("78", 78);
		
		assertTrue("map size is "+map.getSize()+" it should be 13",map.getSize()==13);
		//assertTrue("map balance is"+map.getBalance(),true);
		testDelete(map,97);
		assertTrue("map size is "+map.getSize()+" it should be 12",map.getSize()==12);
		testDelete(map,28);
		assertTrue("map size is "+map.getSize()+" it should be 11",map.getSize()==11);
		testDelete(map,32);
		assertTrue("map size is "+map.getSize()+" it should be 10",map.getSize()==10);
		testDelete(map,65);
		assertTrue("map size is "+map.getSize()+" it should be 9",map.getSize()==9);
		testDelete(map,44);
		assertTrue("map size is "+map.getSize()+" it should be 8",map.getSize()==8);
		
		map = new DataMap<String>();
		map.setContent("44", 44);
		map.setContent("17", 17);
		map.setContent("62", 62);
		map.setContent("32", 32);
		map.setContent("50", 50);
		map.setContent("78", 78);
		map.setContent("48", 48);
		map.setContent("54", 54);
		map.setContent("88", 88);
		
		
		map = new DataMap<String>();
		
		for(long i=0; i<1024; i++){
			map.setContent(""+i, i);
		}
		//assertTrue("map height should be 10 but is "+map.getHeight()+" root: "+map.getRoot().getIndex(),map.getHeight()==10);
		for(long i=255; i<1024; i++){
			map.deleteNode(i);
		}
		//assertTrue("map height should be 9 but is "+map.getHeight()+" root: "+map.getRoot().getIndex(),map.getHeight()==9);
		for(long i=255;i<2048; i++){
			map.setContent(""+i, i);
		}
		//assertTrue("map height should be 11 but is "+map.getHeight()+" root: "+map.getRoot().getIndex(),map.getHeight()==11);
		
		map = new DataMap<String>();
		map.setContent("2", 2);
		map.setContent("4",4);
		map.setContent("1", 1);
		assertTrue("map should not contain 3 but it does.", !map.containsId(3));
		assertTrue("map should contain 2 but it doesn't.", map.containsId(2));
		assertTrue("map should contain 4 but it doesn't.", map.containsId(4));
		assertTrue("map should contain 1 but it doesn't.", map.containsId(1));
		assertTrue("map should not contain 5 but it does.", !map.containsId(5));
	}
	
	private void testDelete(DataMap<String> map, long id){
		assertTrue(id+" is still there",map.getContent(id).equals(id+""));
		map.deleteNode(id);
		assertTrue(id+" should be gone but is: "+map.getContent(id)+")",map.getContent(id)==null);
		
	}

}
