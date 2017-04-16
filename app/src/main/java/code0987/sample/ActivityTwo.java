package code0987.sample;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ActivityTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Now go back!", Snackbar.LENGTH_LONG).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final RelativeLayout content = (RelativeLayout) findViewById(R.id.content);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        View view = content;

                        int x = (view.getLeft() + view.getRight()) / 2;
                        int y = (view.getTop() + view.getBottom()) / 2;
                        float finalRadius = (float) Math.hypot(view.getWidth(), view.getHeight());
                        Animator animator = ViewAnimationUtils.createCircularReveal(view, x, y, 0, finalRadius);
                        view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_blue_dark));
                        animator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                content.addView(new AnimationView(ActivityTwo.this, null));
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        animator.start();
                    }
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class AnimationView extends View {

        ArrayList<Particle> particles = new ArrayList<>();

        Runnable animator = new Runnable() {
            @Override
            public void run() {
                removeCallbacks(this);
                postDelayed(this, 1000 / 30);

                invalidate();
            }
        };

        public AnimationView(Context context, AttributeSet attrs) {
            super(context, attrs);

            post(animator);
        }

        Paint paint = new Paint();
        Path path = new Path();

        protected void onDraw(Canvas c) {
            try {
                c.save();
                c.drawColor(Color.argb(255, 0, 0, 0));

                if (particles.size() < 30) {
                    while (particles.size() < 30)
                        particles.add(new Particle(c));
                }

                for (int i = 0; i < particles.size(); i++) {

                    Particle p = particles.get(i);

                    p.Update(c);
                    p.Draw(c);

                    for (int j = particles.size() - 1; j > i; j--) {
                        float d = (float) Math.sqrt(
                                Math.pow(particles.get(i).X - particles.get(j).X, 2)
                                        +
                                        Math.pow(particles.get(i).Y - particles.get(j).Y, 2)
                        );

                        if (d > p.Proximity)
                            continue;

                        paint.reset();
                        paint.setAntiAlias(true);
                        paint.setColor(p.C);
                        paint.setShader(new LinearGradient(0, 0, 0, 1, particles.get(i).C, particles.get(j).C, Shader.TileMode.MIRROR));
                        paint.setAlpha((int) (((p.Proximity - d) / p.Proximity) * 255));
                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        paint.setStrokeWidth(1.5f);

                        path.reset();
                        path.moveTo(particles.get(i).X, particles.get(i).Y);
                        path.lineTo(particles.get(j).X, particles.get(j).Y);
                        path.close();

                        c.drawPath(path, paint);
                    }
                }

                c.restore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        class Particle {
            public float R = 1.5f;
            public float X;
            public float Y;
            public int C;
            public float Vx;
            public float Vy;
            public float Proximity;

            public Particle(Canvas c) {
                X = (float) Math.random() * c.getWidth();
                Y = (float) Math.random() * c.getHeight();
                C = Color.HSVToColor(new float[]{
                        (float) (360F * Math.random()),
                        0.5F + ((float) Math.random()) / 2F,
                        0.5F + ((float) Math.random()) / 2F
                });
                Vx = ((float) Math.random() - 0.5f) * 5;
                Vy = ((float) Math.random() - 0.5f) * 5;
                Proximity = ((float) Math.random() * 0.30f * (float) Math.sqrt(Math.pow(c.getWidth(), 2) + Math.pow(c.getHeight(), 2)));
            }

            public void Update(Canvas c) {
                if (X > c.getWidth() + 20 || X < -20) {
                    Vx = -Vx;
                }
                if (Y > c.getHeight() + 20 || Y < -20) {
                    Vy = -Vy;
                }

                X += Vx;
                Y += Vy;
            }

            public void Draw(Canvas c) {
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(C);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setStrokeWidth(3f);

                c.drawCircle(X + R, Y + R, R, paint);
            }
        }
    }

}
