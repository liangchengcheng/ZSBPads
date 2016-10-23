package com.lcc.view.edit.editor;

import java.util.List;

public interface SEditorDataI {

    /**
     * 根据绝对路径添加一张图片
     */
    public void addImage(String imagePath);

    /**
     * 根据图片绝对路径数组批量添加一组图片
     */
    public void addImageArray(String[] imagePaths);

    /**
     * 根据图片绝对路径集合批量添加一组图片
     */
    public void addImageList(List<String> imageList);

    /**
     * 获取标题
     */
    public String getTitleData();

    /**
     * 生成编辑数据
     */
    public List<SEditorData> buildEditData();

    /**
     * 获取当前编辑器中图片数量
     */
    public int getImageCount();

    /**
     * 编辑器内容是否为空
     */
    public boolean isContentEmpty();

}
