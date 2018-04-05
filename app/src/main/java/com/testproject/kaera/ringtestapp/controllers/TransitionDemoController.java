package com.testproject.kaera.ringtestapp.controllers;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testproject.kaera.ringtestapp.controllers.base.BaseController;

public class TransitionDemoController extends BaseController {
    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return null;
    }

//    private static final String KEY_INDEX = "TransitionDemoController.index";
//
//    public enum TransitionDemo {
//        VERTICAL("Vertical Slide Animation", R.layout.controller_transition_demo, R.color.blue_grey_300),
//        CIRCULAR("Circular Reveal Animation (on Lollipop and above, else Fade)", R.layout.controller_transition_demo, R.color.red_300),
//        FADE("Fade Animation", R.layout.controller_transition_demo, R.color.blue_300),
//        FLIP("Flip Animation", R.layout.controller_transition_demo, R.color.deep_orange_300),
//        HORIZONTAL("Horizontal Slide Animation", R.layout.controller_transition_demo, R.color.green_300);
//
//        String title;
//        int layoutId;
//        int colorId;
//        TransitionDemo(String title, @LayoutRes int layoutId, @ColorRes int colorId) {
//            this.title = title;
//            this.layoutId = layoutId;
//            this.colorId = colorId;
//        }
//
//        public static TransitionDemo fromIndex(int index) {
//            return TransitionDemo.values()[index];
//        }
//    }
//
//    @BindView(R.id.tv_title) TextView tvTitle;
//    @BindView(R.id.btn_next) FloatingActionButton btnNext;
//    @BindView(R.id.transition_root) View containerView;
//
//    private TransitionDemo transitionDemo;
//
//    public TransitionDemoController(int index) {
//        this(new BundleBuilder(new Bundle())
//                .putInt(KEY_INDEX, index)
//                .build());
//    }
//
//    public TransitionDemoController(Bundle args) {
//        super(args);
//        transitionDemo = TransitionDemo.fromIndex(args.getInt(KEY_INDEX));
//    }
//
//    @NonNull
//    @Override
//    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
//        return inflater.inflate(transitionDemo.layoutId, container, false);
//    }
//
//    @Override
//    protected void onViewBound(@NonNull View view) {
//        super.onViewBound(view);
//
//        View bgView = ButterKnife.findById(view, R.id.bg_view);
//        if (transitionDemo.colorId != 0 && bgView != null) {
//            bgView.setBackgroundColor(ContextCompat.getColor(getActivity(), transitionDemo.colorId));
//        }
//
//        final int nextIndex = transitionDemo.ordinal() + 1;
//        int buttonColor = 0;
//        if (nextIndex < TransitionDemo.values().length) {
//            buttonColor = TransitionDemo.fromIndex(nextIndex).colorId;
//        }
//        if (buttonColor == 0) {
//            buttonColor = TransitionDemo.fromIndex(0).colorId;
//        }
//
//        btnNext.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), buttonColor)));
//        tvTitle.setText(transitionDemo.title);
//    }
//
//    @Override
//    protected String getTitle() {
//        return "Transition Demos";
//    }
//
//    @OnClick(R.id.btn_next) void onNextClicked() {
//        final int nextIndex = transitionDemo.ordinal() + 1;
//
//        if (nextIndex < TransitionDemo.values().length) {
//            getRouter().pushController(getRouterTransaction(nextIndex, this));
//        } else {
//            getRouter().popToRoot();
//        }
//    }
//
//    public ControllerChangeHandler getChangeHandler(Controller from) {
//        switch (transitionDemo) {
//            case VERTICAL:
//                return new VerticalChangeHandler();
//            case CIRCULAR:
//                TransitionDemoController demoController = (TransitionDemoController)from;
//                return new CircularRevealChangeHandlerCompat(demoController.btnNext, demoController.containerView);
//            case FADE:
//                return new FadeChangeHandler();
//            case FLIP:
//                return new FlipChangeHandler();
//            case HORIZONTAL:
//                return new HorizontalChangeHandler();
//            default:
//                return null;
//        }
//    }
//
//    public static RouterTransaction getRouterTransaction(int index, Controller fromController) {
//        TransitionDemoController toController = new TransitionDemoController(index);
//
//        return RouterTransaction.with(toController)
//                .pushChangeHandler(toController.getChangeHandler(fromController))
//                .popChangeHandler(toController.getChangeHandler(fromController));
//    }

}
