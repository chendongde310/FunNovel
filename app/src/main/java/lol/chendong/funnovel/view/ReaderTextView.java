package lol.chendong.funnovel.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import lol.chendong.funnovel.R;

/**
 * ▎作者：此屁天下之绝响
 * ▎Github:www.github.com/chendongde310
 * ▎日期：2017/1/22 - 9:44
 * ▎注释：
 * ▎更新内容：
 */
public class ReaderTextView extends TextView {
    public static final String TYPEFACE_DEFAULT = "TYPEFACE_DEFAULT";
    public static final String TYPEFACE_SONG = "fonts/reader_style_song.ttf";
    private static final String IsSystemBrightness = "IsSystemBrightness"; //使用系统亮度
    private static final String ReaderTextSize = "ReaderTextSize"; //字体大小
    private static final String ReaderBrightness = "ReaderBrightness"; //系统亮度
    private static final String ReaderTypeFace = "ReaderTypeFace"; //字体
    private static final String ReaderStyleInt = "ReaderStyleInt"; //背景颜色
    private final float MinSize = 55f; //最小字体大小
    private final float MaxSize = 125f; //最大字体大小
    private SeekBar mTextSizeSeekBar; //文字大小指示器
    private SeekBar mScreenLightSeekBar;//屏幕亮度指示器
    private Switch mLightSwitch;//使用系统亮度开关
    private boolean isSystemBrightness; //是否使用系统亮度
    private String readerSPName = "mReaderTextSetting"; //SharedPreferencesName
    private int readerBrightnessProgress;   //阅读器亮度 进度指数 （1-100）
    private int readerTextSizeProgress;   //阅读器亮度 进度指数 （1-100）
    private String readerTypeFace;   //阅读器字体
    private int MaxTextSizeProgress = 8;
    private Button mTypeFaceDefault; //默认字体
    private Button mTypeFaceSong; //仿宋字体
    private Button mReaderColorDefault; //默认背景
    private Button mReaderColorEye; //护眼背景
    private Button mReaderColorPink; //淡雅背景
    private Button mReaderColorLight; //夜间背景
    private OnColorListener listener;
    private int readerStyleInt; //背景颜色
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private TextView LightText;
    private LinearLayout linearLayout;
    private Context context;

    public ReaderTextView(Context context) {
        super(context);

    }

    public ReaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getUserSetting();
    }


    /**
     * 获取用户保存的设置信息
     */
    private void getUserSetting() {
        SharedPreferences sp = context.getSharedPreferences(readerSPName, Context.MODE_PRIVATE);
        isSystemBrightness = sp.getBoolean(IsSystemBrightness, true);
        readerBrightnessProgress = sp.getInt(ReaderBrightness, 50);
        readerTextSizeProgress = sp.getInt(ReaderTextSize, 50);
        readerTypeFace = sp.getString(ReaderTypeFace, TYPEFACE_SONG);
        readerStyleInt = sp.getInt(ReaderStyleInt, R.color.reader_defult);
        setReaderBrightnessProgress(readerBrightnessProgress);
        progressConvertTextSize();
        setMyTypeface(readerTypeFace);
    }

    private void setUserSetting() {
        SharedPreferences sp = context.getSharedPreferences(readerSPName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IsSystemBrightness, isSystemBrightness); //是否使用系统亮度
        editor.putInt(ReaderBrightness, readerBrightnessProgress); //保存亮度
        editor.putInt(ReaderTextSize, readerTextSizeProgress);  //保存文字大小
        editor.putString(ReaderTypeFace, readerTypeFace); // 保存字体
        editor.putInt(ReaderStyleInt, readerStyleInt); // 保存主题
        editor.apply();
    }

    /**
     * 添加文本内容
     *
     * @param content
     */
    public void addContent(String title, String content) {
        super.append(Html.fromHtml("<br /><br /><big>" + title + "</big><br /><br />"));
        super.append(Html.fromHtml(content));
        super.append(Html.fromHtml("<br /><br /> <big>【本章完】</big> <br /><br />"));
    }

    /**
     * 设置文本内容
     *
     * @param title
     * @param content
     */
    public void setContent(String title, String content) {
        super.setText("");
        addContent(title, content);
    }


    /**
     * 设置自定义风格字体
     *
     * @param typeface
     */
    public void setMyTypeface(String typeface) {
        readerTypeFace = typeface;
        Typeface tf;
        if (typeface.equals(TYPEFACE_DEFAULT)) {
            tf = Typeface.DEFAULT;
        } else {
            tf = Typeface.createFromAsset(context.getAssets(), typeface); //字体设置
        }
        super.setTypeface(tf);
    }

    /**
     * 改变文字大小
     *
     * @param size
     */
    public void changeTextSize(float size) {
        if (size < MinSize) {
            size = MinSize;
        }
        if (size > MaxSize) {
            size = MaxSize;
        }

        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 创建对话框
     */
    public void creadteDialog() {
        AlertDialog mDialog = new AlertDialog.Builder(context).create();
        mDialog.show();
        Window window = mDialog.getWindow();
        window.setWindowAnimations(R.style.PopupAnimation);
        View view = View.inflate(context, R.layout.view_reader_text_size_dialog, null);
        mDialog.setContentView(view);
        window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Logger.d("对话框关闭，保存用户设置");
                setUserSetting();
            }
        });
        linearLayout = (LinearLayout) view.findViewById(R.id.reader_dialog_ll);
        imageView1 = (ImageView) view.findViewById(R.id.reader_img_light_low);
        imageView2 = (ImageView) view.findViewById(R.id.reader_img_light_high);
        imageView3 = (ImageView) view.findViewById(R.id.reader_size_img_add);
        imageView4 = (ImageView) view.findViewById(R.id.reader_size_img_lass);
        LightText = (TextView) view.findViewById(R.id.reader_light_text);

        setDialogStyle();
        //系统亮度开关
        mLightSwitch = (Switch) view.findViewById(R.id.reader_light_switch);
        mLightSwitch.setChecked(isUseSystemBrightness());
        mLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSystemBrightness(isChecked);
            }
        });
        //字体大小调节
        mTextSizeSeekBar = (SeekBar) view.findViewById(R.id.reader_size_seekbar);
        mTextSizeSeekBar.setMax(MaxTextSizeProgress);
        mTextSizeSeekBar.setProgress(readerTextSizeProgress);
        mTextSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                readerTextSizeProgress = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progressConvertTextSize();
            }
        });
        //亮度调节
        mScreenLightSeekBar = (SeekBar) view.findViewById(R.id.reader_light_seekbar);
        if (isUseSystemBrightness()) {
            mScreenLightSeekBar.setProgress(getScreenBrightness());
        } else {
            mScreenLightSeekBar.setProgress(readerBrightnessProgress);
        }
        mScreenLightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                setReaderBrightnessProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Logger.d("onStartTrackingTouch");
                setSystemBrightness(false);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //字体设置
        mTypeFaceDefault = (Button) view.findViewById(R.id.reader_typeface_default);
        mTypeFaceSong = (Button) view.findViewById(R.id.reader_typeface_song);
        mTypeFaceSong.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setMyTypeface(ReaderTextView.TYPEFACE_SONG);
            }
        });
        mTypeFaceDefault.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setMyTypeface(ReaderTextView.TYPEFACE_DEFAULT);
            }
        });

        //背景设置
        mReaderColorDefault = (Button) view.findViewById(R.id.reader_color_default);
        mReaderColorEye = (Button) view.findViewById(R.id.reader_color_eye);
        mReaderColorPink = (Button) view.findViewById(R.id.reader_color_pink);
        mReaderColorLight = (Button) view.findViewById(R.id.reader_color_light);
        mReaderColorDefault.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                readerStyleInt = R.color.reader_defult;
                postStyle();
            }
        });
        mReaderColorEye.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                readerStyleInt = R.color.reader_eye_1;
                postStyle();
            }
        });
        mReaderColorPink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                readerStyleInt = R.color.reader_pink;
                postStyle();
            }
        });
        mReaderColorLight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                readerStyleInt = R.color.reader_balck;
                postStyle();
            }
        });
    }

    private void setDialogStyle() {
        linearLayout.setBackgroundResource(readerStyleInt);
        if (readerStyleInt != R.color.reader_balck) {
            LightText.setTextColor(getResources().getColor(R.color.reader_text_balck));
            imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_reader_light_low));
            imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_reader_light_high));
            imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_reader_size_add));
            imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_reader_size_lass));
        } else {
            imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_reader_light_low_white));
            imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_reader_light_high_white));
            imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_reader_size_add_white));
            imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_reader_size_lass_white));
            LightText.setTextColor(getResources().getColor(R.color.reader_text_defult));
        }
    }

    /**
     * 传递样式
     */
    private void postStyle() {
        if (listener != null) {
            listener.onClick(readerStyleInt);
        }
        if (linearLayout != null) {
            setDialogStyle();
        }
        if (readerStyleInt != R.color.reader_balck) {
            setTextColor(getResources().getColor(R.color.reader_text_balck));
        } else {
            setTextColor(getResources().getColor(R.color.reader_text_defult));
        }
    }

    public void setOnStyleListener(OnColorListener listener) {
        this.listener = listener;
        postStyle();
    }

    /**
     * 是否使用系统亮度
     *
     * @return
     */
    private boolean isUseSystemBrightness() {
        return isSystemBrightness;
    }

    public void setSystemBrightness(boolean systemBrightness) {
        isSystemBrightness = systemBrightness;
        mLightSwitch.setChecked(systemBrightness);
        changeAppBrightness();
    }

    private int convertProgress() {
        float size = getTextSize();
        return (int) (((size - MinSize) / (MaxSize - MinSize)) * MaxTextSizeProgress);
    }

    /**
     * 通过seekBar设置字体大小
     */
    private void progressConvertTextSize() {
        float size = ((MaxSize - MinSize) / MaxTextSizeProgress) * readerTextSizeProgress;
        changeTextSize(size + MinSize);
    }

    /**
     * 获取屏幕亮度
     *
     * @return
     */
    public int getScreenBrightness() {
        int value = 0;
        ContentResolver cr = context.getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {

        }
        return value * 100 / 255;
    }

    /**
     * 改变当前屏幕亮度
     *
     * @param
     */
    public void changeAppBrightness() {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (isSystemBrightness) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
            this.readerBrightnessProgress = getScreenBrightness();
        } else {
            lp.screenBrightness = readerBrightnessProgress / 100f;
        }
        if (mScreenLightSeekBar != null) {
            mScreenLightSeekBar.setProgress(readerBrightnessProgress);
        }
        window.setAttributes(lp);
    }

    public void setReaderBrightnessProgress(int readerBrightnessProgress) {
        this.readerBrightnessProgress = readerBrightnessProgress;
        changeAppBrightness();
    }

    public interface OnColorListener {
        void onClick(int style);
    }
}
