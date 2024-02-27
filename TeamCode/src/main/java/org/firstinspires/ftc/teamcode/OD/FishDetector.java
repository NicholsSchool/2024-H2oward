package org.firstinspires.ftc.teamcode.OD;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

/**
 * Robot Tensor Flow Vision
 */
public class FishDetector {

    private final TfodProcessor processor;
    private final VisionPortal portal;

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

        portal = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, "Webcam 1"))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setCameraResolution(new Size(640, 480))
                .addProcessor(processor)
                .enableLiveView(true)
                .build();
    }

    /**
     * Gets the Prop Location using the best current Prop Recognition
     *
     * @return the Enum representing the Prop Location
     */
    public double[] getFISH() {
        List<Recognition> recognitions = processor.getFreshRecognitions();

        Recognition rec = recognitions.get(0);

        double centerX = (rec.getLeft() + rec.getRight()) / 2;
        double centerY = (rec.getTop() + rec.getBottom()) / 2;

        return new double[]{centerX, centerY};
    }

    /**
     * Closes the Vision Portal
     */
    public void close() {
        portal.close();
    }
}