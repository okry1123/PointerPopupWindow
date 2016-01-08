PointerPopupWindow
==================

This library will not be updated.

An extended PopupWindow which could add a pointer to the anchor view.You could set your own pointer image, this widget will compute the pointer location for you automatically.

![screen_shot][1]

Usage
==================

0.just copy PointerPopupWindow.java to your project

*For more usage see the `ppw-sample/` folder.*

1.simply use code:

        // create window
        PointerPopupWindow pop = new PointerPopupWindow(context, 200);//specify the window width explicitly
        // set content view
        pop.setContentView(content);
        // set pointer image
        pop.setPointerImageRes(R.drawable.point);
        // show popup window point to the anchor view
        pop.showAsPointer(anchor);
2.set align mode:

        // there are three kinds of built-in align mode are supported:DEFAULT, CENTER_FIX, AUTO_OFFSET
        p.setAlignMode(PointerPopupWindow.AlignMode.AUTO_OFFSET);
3.set margin screen:

        // margin screen are supported to avoid the window stick to the screen
        p.setMarginScreen(30);
        
        
Developed By
============

* OKry - <okry1123@163.com>

License
==================
None

[1]:https://raw.githubusercontent.com/pinguo-marui/PointerPopupWindow/master/ppw-sample/screenshot/screen_shot.jpg
