package lol.chendong.funnovel.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * ▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁
 * ▎作者：chendong
 * ▎Github:www.github.com/chendongde310
 * ▎日期：2017/1/18 - 19:29
 * ▎注释：添加监听滚动到底部
 * ▎更新内容：
 * ▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔
 */
public class SwipScrollView extends ScrollView {

    private OnScrollBottomListener bottomListener;
    private OnScrollViewListener scrollViewListener;
    private int callCount;
    private boolean isSrollToBottom = true;

    public SwipScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollViewScrollToBottom(OnScrollBottomListener l) {
        bottomListener = l;
    }

    public void setOnScrollViewListener(OnScrollViewListener l) {
        scrollViewListener = l;
    }

    public void unRegisterOnScrollViewScrollToBottom() {
        bottomListener = null;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
        View view = this.getChildAt(0);
        if (this.getHeight() + this.getScrollY() == view.getHeight()) {
            callCount++;
            if (callCount == 1) {
                if (bottomListener != null) {
                    bottomListener.srollToBottom();
                }
            }
        } else {
            callCount = 0;
        }
    }

    public void setScrollToBottomEnabled(boolean isSrollToBottom) {
        this.isSrollToBottom = isSrollToBottom;
    }

    public boolean isSrollToBottom() {
        return isSrollToBottom;
    }

    public interface OnScrollBottomListener {
        void srollToBottom();
    }

    public interface OnScrollViewListener {

        void onScrollChanged(SwipScrollView scrollView, int x, int y, int oldx, int oldy);

    }
}
