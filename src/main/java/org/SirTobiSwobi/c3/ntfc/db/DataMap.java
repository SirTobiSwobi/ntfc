package org.SirTobiSwobi.c3.ntfc.db;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataMap<T> {
	
	//private AVLTreeNode<T> root;
	//private long size;
	private byte[] contentHash;
	final static Logger logger = LoggerFactory.getLogger(DataMap.class);
	
	private HashMap<Long,T> dataMap;

	public DataMap() {
		super();
		//this.size=0;
		this.contentHash = new byte[32]; //SHA-256 produces 256 bit digests, what equals 32 bytes. 
		dataMap = new HashMap<Long,T>();
	}
	
	public synchronized void setContent(T content, long id){
		/*
		if(root==null){
			root=new AVLTreeNode<T>(this,content,id);
			size=1;
		}else{
			root.setContent(id, content);
		}*/
		dataMap.put(id, content);
		try{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] key = content.toString().getBytes();
		byte[] hash = md.digest(key);
		for(int i=0;i<hash.length;i++){
			this.contentHash[i]=(byte) (this.contentHash[i]+hash[i]);
		}
		
		}catch(Exception cnse){
			logger.error("Failed to create content hash. Message: "+cnse.getLocalizedMessage());
		}
	}
	
	public ArrayList<T> toArrayList(){		
		ArrayList<T> allContent= new ArrayList<T>();
		allContent.addAll(dataMap.values());
		return allContent;
		
	}
	
	/*
	public void incrementSize(){
		size++;
	}*/
	
	public long getSize() {
		return (long)dataMap.size();
	}

	public T getContent(long id){		
		return dataMap.get(id);
	}
	
	/*
	protected void rebalance(long id){
		if(!root.treeIsBalanced()){
			AVLTreeNode<T> unbalanced = root.getUnbalancedAncestor(id);
			if(unbalanced.getBalance()>1&&id<unbalanced.getLeft().getIndex()){
				rightRotate(unbalanced);
			}else if(unbalanced.getBalance()<-1&&id>unbalanced.getRight().getIndex()){
				leftRotate(unbalanced);
			}else if(unbalanced.getBalance()>1&&id>unbalanced.getLeft().getIndex()){
				leftRotate(unbalanced.getLeft());
				rightRotate(unbalanced);			
			}else if(unbalanced.getBalance()<-1&&id<unbalanced.getRight().getIndex()){
				rightRotate(unbalanced.getRight());
				leftRotate(unbalanced);
			}
		}
	}
	
	protected void rebalanceUnbalancedNode(long id){
		if(!root.treeIsBalanced()){
			AVLTreeNode<T> unbalanced = root.getById(id);
			if(unbalanced!=null){
				if(unbalanced.getBalance()>1&&id<unbalanced.getLeft().getIndex()){
					rightRotate(unbalanced);
				}else if(unbalanced.getBalance()<-1&&id>unbalanced.getRight().getIndex()){
					leftRotate(unbalanced);
				}else if(unbalanced.getBalance()>1&&id>unbalanced.getLeft().getIndex()){
					leftRotate(unbalanced.getLeft());
					rightRotate(unbalanced);			
				}else if(unbalanced.getBalance()<-1&&id<unbalanced.getRight().getIndex()){
					rightRotate(unbalanced.getRight());
					leftRotate(unbalanced);
				}
			}
		}
		
	}
	
	public void leftRotate(AVLTreeNode<T> n){
		AVLTreeNode<T> z = n;
		AVLTreeNode<T> y = n.getRight();
		AVLTreeNode<T> t2 = y.getLeft();
		
		AVLTreeNode<T> zParent = z.getParent();
		boolean isRoot = false;
		if(n==root){
			isRoot=true;
		}
		boolean leftChild = false;
		if(n!=root&&zParent.getLeft()==n){
			leftChild = true;
		}
		z.setRight(t2);
		
		if(t2!=null){
			t2.setParent(z);
		}
		y.setLeft(z);
		z.setParent(y);
		if(isRoot){
			this.root=y;
		}else{
			y.setParent(zParent);
			if(leftChild){
				zParent.setLeft(y);
			}else{
				zParent.setRight(y);
			}
		}
	}
	
	public void rightRotate(AVLTreeNode<T> n){
		AVLTreeNode<T> z = n;
		AVLTreeNode<T> y = n.getLeft();
		AVLTreeNode<T> t3 = y.getRight();
		AVLTreeNode<T> zParent = z.getParent();
		boolean isRoot = false;
		if(n==root){
			isRoot = true;
		}
		boolean leftChild = false;
		if(n!=root&&zParent.getLeft()==n){
			leftChild = true;
		}
		z.setLeft(t3);
		if(t3!=null){
			t3.setParent(z);
		}
		y.setRight(z);
		z.setParent(y);
		if(isRoot){
			this.root=y;
		}else{
			y.setParent(zParent);
			if(leftChild){
				zParent.setLeft(y);
			}else{
				zParent.setRight(y);
			}
		}
	}
	
	public int getBalance(){
		return root.getBalance();
	}
	*/
	public long getMaxId(){
		ArrayList<Long> usedIds = new ArrayList<Long>();
		usedIds.addAll(dataMap.keySet());
		long maxId=0;
		for(int i=0;i<usedIds.size();i++){
			if(maxId<usedIds.get(i)){
				maxId=usedIds.get(i);
			}
		}
		return maxId;
		
	}
	
	public boolean containsId(long id){
		return dataMap.containsKey(id);
			
	}
	
	public synchronized void deleteNode(long id){
		try{
			T content = dataMap.get(id);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] key = content.toString().getBytes();
			byte[] hash = md.digest(key);
			for(int i=0;i<hash.length;i++){
				this.contentHash[i]=(byte) (this.contentHash[i]-hash[i]);
			}

		}catch(Exception cnse){
			logger.error("Failed to create content hash. Message: "+cnse.getLocalizedMessage());
		}
		dataMap.remove(id);

	}
	
	/*
	protected AVLTreeNode<T> getRoot(){
		return root;
	}*/
	
	protected void empty(){
		this.dataMap=new HashMap<Long,T>();
		//this.size=0;
	}
	
	/*
	protected void setRoot(AVLTreeNode<T> root){
		this.root=root;
	}
	
	public int getHeight(){
		return root.getHeight();
	}
	*/
	public byte[] getContentHash(){
		
		return contentHash;
	}
	
	public ArrayList<Long> getUsedIds(){
		ArrayList<Long> usedIds = new ArrayList<Long>();
		usedIds.addAll(dataMap.keySet());
		return usedIds;
	}

}
