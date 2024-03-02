# H2Oward v3
### This is the repository for the third version of Electric Mayhem's Fish-Driven robot.

**WARNING:** This codebase relies on an SSD Mobilenet 640x640 Object Detection model WITHOUT OpenCV Tracking. This results in horrible performance from the REV Control Hub. Only use this model with external Android devices (4-8 cores, >2.8GHz) and NOT the Rockchip-based Control Hub v1.0.

## Hardware:

- **Main Robot Controller:** Xiaomi Pad 6
- **Webcam:** Logitech C920 Streamer Webcam
- **Drivetrain** Kiwi (2 Wheels at the front, 1 at the back)

## The Model

This version of H2Oward uses a custom locally-trained TensorFlow object detection model. The model can be found under:
```TeamCode\src\main\assets\howardDetector.tflite```
and is configured for the Android TFOD Libraries.

The model was trained using thousands of images of Howard and the SSD Mobilenet FPNLite 640x640 pre-trained model. This model accurately detects Howard whenever he is in frame. It is not guaranteed to work under all lighting conditions or camera resolutions.

## The Drive Calculations

Converting the fish's position into a pseudo-joystick input takes two steps.

### Step 1: Fish Coordinates
This first tep involves calculating the pixel value of the center of Howard by averaging the bounds of the model's detection. This point is then shifted so that (0, 0) represents the center of the image instead of a corner.

### Step 2: Vector Driving
The coordinates are then converted into X and Y values ranging from -1 to 1. These are then used to create a custon Vector object, which is then passed into the drive function.

## Motion Profiling
The drive method contains special functionality to implement Trapezoid Profiling for the vision input. This dampens any sudden changes in the inputs to keep the motion of the robot smooth. For example, if the robot were to lose track of the fish, it would automatically return (0, 0). This would ordinarily bring the robot to a stop as fast as possible. However, because of Motion Profiling, the robot would instead be slowly brought to a stop.