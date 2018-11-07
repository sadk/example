package org.lsqt.components.context;

/**
 * 容器实例化bean和自身的生命周期轨迹定义
 * @author Sky
 *
 */
@Deprecated
public interface LifeCycle {
	
	//配置启动：~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	interface Config{
		/**
		 * 解析配置文件或注解信息前执行
		 * @param exe
		 */
		void beforeBeanMetaResolve();
		
		/**
		 *  解析配置文件或注解信息后执行
		 * @param exe
		 */
		void afterBeanMetaResolve();
	}
	
	
	
	//对象启动:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	interface Bean{
		interface CycleAfterBeanProperty{
			/**
			 * bean对象的属性全部设值后执行
			 * @param exe
			 */
			void afterBeanPropertySet();
		}
		
		interface CycleBeanComplete{
			/**
			 * 在周期BeanPropertySet后执行，如：xml配置的init-method调用
			 * 注：！！！有配置factory-bean和factory-method 在xml里，跟据bean的class配置，是不能产生实例的,
			 * 需解析引用的bean或匿名factory-bean实例
			 * @param exe
			 */
			void afterBeanComplete();
		}
		
		interface CycleBeanDestory{
			/**
			 * <pre>
			 * 容器关闭之前执行，如：xml配置的destroy-method调用 .
			 * 注：一般来说是tomcat容器关闭后，所有bean执行的方法，如果需要垃圾回收
			 * 对象时执行，请覆盖Object.finalize()方法，并在方法体内调用此方法,
			 * 笔者不推荐使用该访式,因为：
			 * 1.垃圾回收器是否会执行该方法及何时执行该方法，都是不确定的;
			 * 2.finalize()方法有可能使对象复活，使它恢复到可触及状态;
			 * 3.垃圾回收器在执行finalize()方法时，如果出现异常，垃圾回收器不会报告异常，
			 * 程序继续正常运行,资源并没有释放!!!
			 * </pre>
			 */
			void beforeDestory();
		}
	}
}
