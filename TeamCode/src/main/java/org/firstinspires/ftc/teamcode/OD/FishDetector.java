package org.firstinspires.ftc.teamcode.OD;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.math_utils.Point;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import java.util.List;

/**
 * Robot Tensor Flow Vision
 */
public class FishDetector {

    private final TfodProcessor processor;
    private final VisionPortal portal;
    private Point fishCoords = new Point(0.0, 0.0);

    /**
     * Initializes the TensorFlowVision
     *
     * @param hwMap the hardware map
     */
    public FishDetector(HardwareMap hwMap) {
        processor = new TfodProcessor.Builder()
                .setModelAssetName("howardDetector.tflite")
                .setModelLabels(new String[]{"FISH"})
                .setIsModelTensorFlow2(true)
                .setUseObjectTracker(false)
                .setMaxNumRecognitions(1)
                .setNumDetectorThreads(4)
                .setNumExecutorThreads(4)
                .build();
        processor.setClippingMargins(420, 0, 420, 0);

        portal = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, "Webcam 1"))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setCameraResolution(new Size(1920, 1080))
                .addProcessor(processor)
                .enableLiveView(true)
                .build();
    }

    /**
     * Gets the Prop Location using the best current Prop Recognition
     * To be called once every loop ONLY.
     * Inference takes 130-180 ms on Xiaomi Pad 6
     * 180-230 ms on Google Pixel 6 Pro
     * ~8000 ms on REV Control Hub     * 
     */
    public void update() {
        List<Recognition> recognitions = processor.getRecognitions();

        Recognition rec;
        try {
            rec = recognitions.get(0);

            double centerX = (rec.getLeft() + rec.getRight()) / 2;
            double centerY = (rec.getTop() + rec.getBottom()) / 2;

            fishCoords = new Point(centerX, centerY);

        }
        catch (Exception e) {
            fishCoords = new Point(0.0, 0.0);
        }
    }

    public Point getFishCoords() {
        return fishCoords;
    }

    public Point getXYInput() {

        double procX = (fishCoords.x - 960)/960;
        double procY = (-fishCoords.y + 540)/540;

        return new Point(procX, procY);
    }

    /**
     * Closes the Vision Portal
     */
    public void quit() {
        portal.close();
    }
}