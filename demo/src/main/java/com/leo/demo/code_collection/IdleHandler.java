package com.leo.demo.code_collection;

import android.os.Looper;
import android.os.MessageQueue;

/**
 * Created by Leo on 2019/7/8.
 */
public class IdleHandler {
    // https://mp.weixin.qq.com/s/KpeBqIEYeOzt_frANoGuSg

    /**
     * IdleHandler 可以用来提升性能，主要用在我们希望能够在当前线程消息队列空闲时做些事情
     * （譬如 UI线程在显示完成后，如果线程空闲我们就可以提前准备其他内容）的情况下，
     * 不过最好不要做耗时操作
     * <p>
     * 返回值：返回值是ture的话，执行完queueIdle方法之后会保留这个IdleHandler，
     * 反之则删除这个IdleHandler
     */
    public static void test() {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                return false;
            }
        });
    }
}
