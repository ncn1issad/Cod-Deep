package org.firstinspires.ftc.teamcode.opmodes.auto.utilities;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PathCreator extends PoseMechanismFactory {
    default Map<PoseMechanism, PathChain> createPaths(Follower follower) {
        PoseMechanism[] array = poseMechanismFactory();
        Map<PoseMechanism, PathChain> result = new HashMap<>();

        int i = 0;
        while (i < array.length) {
            PoseMechanism current = array[i];
            // Skip if current is a control point
            if (current.isControl()) {
                i++;
                continue;
            }
            // Determine if current is a curve
            boolean curve = current.isCurve();
            // Gather all control points
            List<Pose> controlPoints = new ArrayList<>();
            int j = i + 1;
            while (j < array.length && !array[j].isControl()) {
                controlPoints.add(array[j].getPose());
                j++;
            }
            // If we run out of points, break
            if (j == array.length) {
                break;
            }

            PoseMechanism next = array[j];
            Pose start = current.getPose();
            Pose end = next.getPose();

            BezierCurve segment;
            if (curve && !controlPoints.isEmpty()) {
                List<Point> points = new ArrayList<>();
                points.add(new Point(start));
                for (Pose controlPoint : controlPoints) {
                    points.add(new Point(controlPoint));
                }
                points.add(new Point(end));
                // Convert the array of points
                segment = new BezierCurve(points.toArray(new Point[0]));
            }
            else {
                segment = new BezierLine(new Point(start), new Point(end));
            }

            PathBuilder builder = follower.pathBuilder()
                    .addPath(segment)
                    .setLinearHeadingInterpolation(start.getHeading(), end.getHeading());

            PathChain chain = builder.build();
            // Store the chain
            result.put(current, chain);
            // Move on to the next endpoint
            i = j;
        }
        return result;
    }
}
