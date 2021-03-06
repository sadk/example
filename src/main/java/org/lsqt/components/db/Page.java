package org.lsqt.components.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author yuanmm
 *
 * @param <T>
 */
public interface Page<T> {
	
	int DEFAULT_PAGE_SIZE = 20;
	int DEFAULT_PAGE_INDEX = 0;
	
	int getPageIndex() ;
	void setPageIndex(int pageIndex) ;
	
	int getPageSize();
	void setPageSize(int pageSize);
	
	Collection <T> getData();
	void setData(Collection <T> data);
	
	boolean getHasPrevious();
	void setHasPrevious(boolean has);
	
	boolean getHasNext();
	void setHasNext(boolean has);
	
	long getTotal();
	void setTotal(long total);
	
	long getPageCount();
	void setPageCount(long pageCount);
	
	//一般用于前端，供用户从下拉框里选择分页大小用
	Collection<Integer> getPageSizeList();
	void setPageSizeList(Collection<Integer> pageSizeList);
	
	/**数据钩子,用于附带其它上下文需要的数据**/
	Object getHook();
	void setHook(Object hookObject);

	/**
	 * 分页动作接口
	 * 
	 * @author mm
	 *
	 */
	interface Action {
		int MAX_PAGE_SIZE_LOOPED = 10000; // 报表数据量过大导出，进行分页加载数据, 分页的阀值
		/**
		 * 循环执行翻页时，调用的模板函数
		 * 
		 * @param currPageIndex  当前页索引
		 * @param currPageSize  当前分页大小
		 * @param currPageData  当前页数据
		 */
		void doNextPage(int currPageIndex, int currPageSize, List<Map<String, Object>> currPageData);
	}
	
	class PageModel<T> implements Page<T>,java.io.Serializable {
		
		private static final long serialVersionUID = 2715047046345293259L;
		
		private int pageSize = DEFAULT_PAGE_SIZE;
		private int pageIndex = DEFAULT_PAGE_INDEX;
		
		private long total = 0;
		private long pageCount = 0;
		private boolean hasNext = false;
		private boolean hasPrevious = false;
		private Collection<T> data = new ArrayList<T>();
		private Object hook ;
		
		private Collection<Integer> pageSizeList = Arrays.asList(10,20,50,100);
		
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		public int getPageIndex() {
			return pageIndex;
		}
		public void setPageIndex(int pageIndex) {
			this.pageIndex = pageIndex;
		}
		public long getTotal() {
			return total;
		}
		public void setTotal(long total) {
			this.total = total;
		}
		public long getPageCount() {
			return pageCount;
		}
		public void setPageCount(long pageCount) {
			this.pageCount = pageCount;
		}
		public boolean getHasNext() {
			return this.hasNext;
		}
		public void setHasNext(boolean hasNext) {
			this.hasNext = hasNext;
		}
		public boolean getHasPrevious() {
			return this.hasPrevious;
		}
		public void setHasPrevious(boolean hasPrevious) {
			this.hasPrevious = hasPrevious;
		}
		public Collection<T> getData() {
			return data;
		}
		public void setData(Collection<T> data) {
			this.data = data;
		}
		public Collection<Integer> getPageSizeList() {
			return pageSizeList;
		}
		public void setPageSizeList(Collection<Integer> pageSizeList) {
			this.pageSizeList = pageSizeList;
		}
		public Object getHook() {
			return hook;
		}
		public void setHook(Object hook) {
			if(this == hook) {
				throw new IllegalStateException("数据构子不能引用自己");
			}
			this.hook = hook;
		}

	}
}
