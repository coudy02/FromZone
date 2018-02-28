package eshore.gdtel.com.restartplan.helper;

/**
 * Created by Administrator on 2018/2/27.
 * 这两种模式，最大的区别是，一个省时间，一个省空间。
 * 饿汉式的特点是一开始就加载了，如果说懒汉式是“时间换空间”，
 * 那么饿汉式就是“空间换时间”，因为一开始就创建了实例，所以每次用到的之后直接返回就好了。
 */

public class TestSingleton {
    /*
     懒汉模式，需要才创建
     */
    private static TestSingleton mSingleton = null;
    /*
     私有构造函数
    */
    private TestSingleton(){
        System.out.println("-->懒汉式单例模式开始调用构造函数");
    }

    public static TestSingleton getmSingleton(){
        if(mSingleton == null){
            System.out.println("-->懒汉式构造函数的实例当前并没有被创建");
            mSingleton = new TestSingleton();
        } else {
            System.out.println("-->懒汉式构造函数的实例已经被创建");
        }
        System.out.println("-->方法调用结束，返回单例");
        return mSingleton;
    }

//    /*
//      饿汉模式
//     */
//    private static TestSingleton mSingleton = new TestSingleton();
//    /*
//      私有构造函数
//     */
//    private TestSingleton(){
//    }
//    public static TestSingleton getmSingleton(){
//        return mSingleton;
//    }
}
