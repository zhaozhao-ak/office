/*
 * 文件名称:          AnimationListener.java
 *  
 * 编译器:            android2.2
 * 时间:              下午5:22:44
 */
package com.wxiwei.office.pg.animate;

import android.graphics.Rect;


/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2012-11-21
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:              
 * <p>
 * <p>
 */
public interface IAnimation
{
    public class AnimationInformation
    {
        //rotation during animating 
        public static final int ROTATION = 720;
        
        public AnimationInformation(Rect postion, int alpha, int angle)
        {
            if(postion != null)
            {
                this.postion = new Rect(postion);
            }
            
            this.alpha = alpha;
            this.angle = angle;
        }
        
        /**
         * 
         * @param postion
         * @param alpha
         * @param angle
         */
        public void setAnimationInformation(Rect postion, int alpha, int angle)
        {
            if(postion != null)
            {
                this.postion = new Rect(postion);
            }
            this.alpha = alpha;
            this.angle = angle;
            
            progress = 0;
        }
        
        /**
         * current progress
         * @param progress
         */
        public void setProgress(float progress)
        {
            this.progress = progress;
        }
        
        /**
         * current progress
         * @return
         */
        public float getProgress()
        {
            return progress;
        }
        
        /**
         * 
         * @param postion
         */
        public void setPostion(Rect postion)
        {
            if(postion != null)
            {
                this.postion = new Rect(postion);
            }
        }
        
        /**
         * get current shape postion
         * @return
         */
        public Rect getPostion()
        {
            return postion;
        }
        
        public void setAlpha(int alpha)
        {
            this.alpha = alpha;
        }
        
        /**
         * get  current alpha
         * @return
         */
        public int getAlpha()
        {
            return alpha;
        }
        
        public void setAngle(int angle)
        {
            this.angle = angle;
        }
        
        /**
         * get current roration
         * @return
         */
        public int getAngle()
        {
            return angle;
        }
        
        /**
         * 
         */
        public void dispose()
        {
            postion = null;
        }
        
        private int alpha;
        private int angle;
        private Rect postion;
        
        private float progress;
    }
   
    /**
     * 
     * @return
     */
    public byte getAnimationStatus();
    
    /**
     * start animation
     */
    public void start();
    
    /**
     * stop animation 
     */
    public void stop();
    
    /**
     * update animation parameters
     * @param frameIndex
     */
    public void animation(int frameIndex);
    
    /**
     * get current animation information of frame when animating
     * @return
     */
    public AnimationInformation getCurrentAnimationInfor();
    
    /**
     * 
     * @return
     */
    public ShapeAnimation getShapeAnimation();
    
    /**
     * get animation duration(ms)
     * @return
     */
    public int getDuration();
    
    /**
     * set animation duration(ms)
     */
    public void setDuration(int duration);
    
    /**
     * 
     */
    public int getFPS();
    
    public void dispose();
}
