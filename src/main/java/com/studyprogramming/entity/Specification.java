package com.studyprogramming.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="specification")
public class Specification extends BaseEntity {
    private String chipName;

    private String CPUCore;

    private String GPUCore;

    private String neuralEngine;

    private String memory;

    private String SSD;

    private String displayType;

    private String size;

    private String resolution;

    private String brightness;

    private String camera;

    private String video;

    private String audio;

    private String inputs;

    private String wireless;

    private String inputOrOutputAndExpansions;

    private String operatingSystem;

    private String inTheBox;

    @OneToOne(mappedBy = "specification")
    private Product product;

    public String getChipName() {
        return chipName;
    }

    public void setChipName(String chipName) {
        this.chipName = chipName;
    }

    public String getCPUCore() {
        return CPUCore;
    }

    public void setCPUCore(String CPUCore) {
        this.CPUCore = CPUCore;
    }

    public String getGPUCore() {
        return GPUCore;
    }

    public void setGPUCore(String GPUCore) {
        this.GPUCore = GPUCore;
    }

    public String getNeuralEngine() {
        return neuralEngine;
    }

    public void setNeuralEngine(String neuralEngine) {
        this.neuralEngine = neuralEngine;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getSSD() {
        return SSD;
    }

    public void setSSD(String SSD) {
        this.SSD = SSD;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getInputs() {
        return inputs;
    }

    public void setInputs(String inputs) {
        this.inputs = inputs;
    }

    public String getWireless() {
        return wireless;
    }

    public void setWireless(String wireless) {
        this.wireless = wireless;
    }

    public String getInputOrOutputAndExpansions() {
        return inputOrOutputAndExpansions;
    }

    public void setInputOrOutputAndExpansions(String inputOrOutputAndExpansions) {
        this.inputOrOutputAndExpansions = inputOrOutputAndExpansions;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getInTheBox() {
        return inTheBox;
    }

    public void setInTheBox(String inTheBox) {
        this.inTheBox = inTheBox;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
