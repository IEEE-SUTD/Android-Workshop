package com.sutd.helloar;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private ViewRenderable renderable;
    private static final String GLTF_ASSET =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf";
    private AnchorNode anchorNode;
    private TransformableNode transformableNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remove anchor
        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(view -> {
            anchorNode.removeChild(transformableNode);
            anchorNode.getAnchor().detach();
        });

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            // Render 2D view
            ViewRenderable.builder()
                    .setView(this, R.layout.hello_ar)
                    .build()
                    .thenAccept(viewRenderable -> {renderable = viewRenderable;})
                    .exceptionally(
                            throwable -> {
                                Toast toast = Toast.makeText(this, "Unable to load renderable " +
                                        GLTF_ASSET, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return null;
                            });

            //Gets gltf model from online link, renders 3D obj
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
                    .thenAccept(modelRenderable -> addModelToScene(hitResult.createAnchor(), modelRenderable, renderable))
                    .exceptionally(
                            throwable -> {
                                Toast toast = Toast.makeText(this, "Unable to load renderable " +
                                        GLTF_ASSET, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return null;
                            });

        });
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable, ViewRenderable renderable) {
        anchorNode = new AnchorNode(anchor); //Create anchorNode based on pos of anchor

        transformableNode = new TransformableNode(arFragment.getTransformationSystem()); //Use transformableNode to move or rotate
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);

        Node node = new Node(); //Use node to not have any changes to scaling
        node.setParent(transformableNode);
        node.setRenderable(renderable);
        node.setLocalPosition(new Vector3(0.0f, 0.5f, 0.0f));

        arFragment.getArSceneView().getScene().addChild(anchorNode); //add anchorNode to scene

        transformableNode.select();
    }
}
