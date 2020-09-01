package com.example.augmentedimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    private CustomArFragment arFragment;
    private static final String GLTF_ASSET =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);  //Make reference to AR fragment

        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdate);

    }

    private void onUpdate(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        Collection<AugmentedImage> images = frame.getUpdatedTrackables(AugmentedImage.class);

        for (AugmentedImage image: images) {
            if (image.getTrackingState() == TrackingState.TRACKING) { //check if duck img is tracked -> create anchor if tracked
                if (image.getName().equals("duck")) {
                    Anchor anchor = image.createAnchor(image.getCenterPose());

                    createModel(anchor);
                }
            }
        }
    }

    private void createModel(Anchor anchor) {
        //Gets gltf model from online link
        ModelRenderable.builder()
                .setSource(this, RenderableSource.builder().setSource(
                        this,
                        Uri.parse(GLTF_ASSET),
                        RenderableSource.SourceType.GLTF2)
                        .setScale(0.1f) //Scale the original model to 50%
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build())
                .setRegistryId(GLTF_ASSET)
                .build()
                .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable))
                .exceptionally(
                        throwable -> {
                            Toast toast = Toast.makeText(this, "Unable to load renderable " +
                                    GLTF_ASSET, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor); //Create anchorNode based on pos of anchor
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem()); //Use transformableNode to move or rotate
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode); //add anchorNode to scene

        transformableNode.select();
    }

    public void setupDatabase(Config config, Session session) {
        Bitmap duckBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.duck); //Bitmap -> AugmentedImageDb -> Session
        AugmentedImageDatabase aid = new AugmentedImageDatabase(session);
        aid.addImage("duck", duckBitmap);
        config.setAugmentedImageDatabase(aid);
    }
}
