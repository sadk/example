package org.lsqt.report.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class BigArrayList<T> implements Iterable<T> {
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("{");
		if (theItems != null && theItems.length > 0) {
			for (int i=0;i<theItems.length;i++) {
				sb.append(theItems[i]);
				if(i!=theItems.length -1) {
					sb.append(",");
				}
			}
		}
		sb.append("}");
		return sb.toString();
	}
	
    //定义默认数组大小
    private static final int DEFAULT_CAPACITY = 10;
    //定义数组当前大小
    private int theSize;
    //定义存储的数组
    private T[] theItems;

    //构造函数，实现初始化操作
    public BigArrayList(){
        doClear();
    }

    public void clear(){
        doClear();
    }

    /**
     * 实现清空操作：设置数组大小为0，且设置默认大小的数组
     */
    public void doClear(){
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    //获取到ArrayList的长度
    public int size(){
        return theSize;
    }

    //判断ArrayList是否为空
    public boolean isEmpty(){
        return size() == 0;
    }

    //提供设置内置数组的大小
    public void trimToSize(){
        ensureCapacity(size());
    }

    //获取到对应的数据
    public T get( int idx){
        if ( idx < 0 || idx >= size() )
            throw new ArrayIndexOutOfBoundsException();
        return theItems[idx];
    }

    /**
     * 进行更新数据，并返回原来的数据
     * @param idx
     * @param newVal
     * @return
     */
    public T set( int idx, T newVal ){
        if ( idx < 0 || idx >= size() )
            throw new ArrayIndexOutOfBoundsException();
        T old = theItems[idx];
        theItems[idx] = newVal;
        return old;
    }

    /**
     * 重新构造内置数组，以改变其大小
     * @param newCapacity
     */
    private void ensureCapacity(int newCapacity) {
        if (newCapacity < size())
            return;
        T [] old = theItems;
        theItems = (T[]) new Object[newCapacity];
        for ( int i = 0; i < size(); i ++ ){
            theItems[i] = old[i];
        }
    }

    //默认方式添加数据
    public boolean add(T x){
        add(size(), x);
        return true;
    }

    //实现添加数据
    private void add(int idx, T x) {
        //theItems.length表示其长度，而theSize表示为具体内容的长度，注意两者区别
        //若相等，表示数组要满了，因此要扩充数组
        if (theItems.length == size()){
            ensureCapacity(size() * 2 + 1);
        }
        //对数组中的数据进行移动，整体往右移动
        for (int i = theSize; i > idx; i --){
            theItems[i] = theItems[i-1];
        }
        //进行赋值，并size++
        theItems[idx] = x;
        theSize ++;
    }

    //添加一个集合
    public void addAll(Collection<T> collection){
        if (collection == null){
            throw new NullPointerException();
        }
        for (T c : collection){
            add(c);
        }
    }

    //实现删除数据，并返回删除的数据
    public T remove(int idx){
        T removeItem = theItems[idx];
        //移动数组位置，整体往左移动
        for ( int i = idx; i < size() - 1; i ++){
            theItems[i] = theItems[i + 1];
        }
        theSize --;
        return removeItem;
    }

    //实现删除某个元素
    public boolean remove(T x){
        //先判断是否存在该元素
        if (indexOf(x) != -1){
            //获取到元素的位置，再进行删除操作
            remove(indexOf(x));
            return true;
        }
        return false;
    }

    //是否包含该元素
    public boolean contains(T x){
        return indexOf(x) != -1;
    }

    //查找元素最早出现的位置
    public int indexOf(T x){
        int index = 0;
        if (x == null){
            for (int i = 0; i < theSize; i ++){
                if (theItems[i] == null){
                    return index;
                }
                index ++;
            }
        } else {
            for (int i = 0; i < theSize; i ++){
                if (x.equals(theItems[i])){
                    return index;
                }
                index ++;
            }
        }
        return -1;
    }

    //查找元素最后出现的位置
    public int lastIndexOf(T x){
        int index = theSize-1;
        if (x == null){
            for (int i = theSize - 1; i >= 0; i --){
                if (theItems[i] == null){
                    return index;
                }
                index --;
            }
        } else {
            for (int i = theSize - 1; i >= 0; i --){
                if (x.equals(theItems[i])){
                    return index;
                }
                index --;
            }
        }
        return -1;
    }

    //返回为一个数组
    public T[] toArray(){
        return theItems;
    }


    public Iterator<T> iterator(){
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T>{
        //记录当前位置
        private int current = 0;

        //判断是否存在下一项
        @Override
        public boolean hasNext() {
            return current < size();
        }

        //返回当前位置的元素
        @Override
        public T next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            return theItems[current++];
        }
        //删除当前位置，注意这里是--current，对应上面next的current++
        public void remove(){
        	BigArrayList.this.remove(--current);
        }
    }

    public static void main(String[] args) {
    	BigArrayList<String> list =new BigArrayList<>();
    	list.add("fdsd") ;
    	list.add("222");
    	list.add("fd333sd");
    	list.add("444");
    	System.out.println(list);
    	
	}
}
 
