package com.gy.designpattern.template.callback;

/**
 * @ClassName DoCallback
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-21 23:28
 */
public interface DoCallback {


	public void setup(String userId);

	public void realDo(String userId);

	public void cleanup(String userId);


}
