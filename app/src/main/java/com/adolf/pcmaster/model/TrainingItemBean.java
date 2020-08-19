package com.adolf.pcmaster.model;

/**
 * @program: PCMaster
 * @description:
 * @author: Adolf
 * @create: 2020-08-19 17:16
 **/
public class TrainingItemBean {
    private String model;
    private int loop;
    private String name;

    public TrainingItemBean(String model, int loop, String name) {
        this.model = model;
        this.loop = loop;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getLoop() {
        return loop;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    @Override
    public String toString() {
        return "TrainingItemBean{" +
                "model='" + model + '\'' +
                ", loop=" + loop +
                '}';
    }
}
