package com.example.photoeditormain

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import javafx.stage.Stage

class FaceWindow {
    private var root = AnchorPane()
    private val width = 1280.0
    private val height = 720.0
    private var scene = Scene(root, width, height)

    fun start(): Scene {
        root.children.add(createButtons())

        val end = EndNode()
        end.layoutX = width - end.rootPane!!.prefWidth - 30
        end.layoutY = height / 16
        root.children.add(end)

        return scene
    }

    private fun getStringNode(buttonTypes: ButtonTypes): String {
        val string = when (buttonTypes) {
            ButtonTypes.INT -> "int"
            ButtonTypes.FLOAT -> "float"
            ButtonTypes.STRING -> "string"
            ButtonTypes.IMAGE -> "image"
            ButtonTypes.SEPIA -> "sepia"
            ButtonTypes.GREY -> "grey"
            ButtonTypes.INVERT -> "invert"
            ButtonTypes.BRIGHT -> "bright"
            ButtonTypes.GAUSSIAN -> "gaussian"
            ButtonTypes.SCALE_PIXEL -> "scale pixel"
            ButtonTypes.SCALE -> "scale"
            ButtonTypes.MOVE_PIXEL -> "move pixel"
            ButtonTypes.MOVE -> "move"
            ButtonTypes.ROTATE -> "rotate"
            ButtonTypes.ADD_TEXT_PIXEL -> "add text pixel"
            ButtonTypes.ADD_TEXT -> "add text"
        }
        return string
    }

    private fun createButtons(): VBox {
        val vBox = VBox(20.0)
        vBox.style = "-fx-padding: 20px 10px; -fx-margin: 10px; -fx-background-color: #5F9EA0;" +
                "-fx-background-radius: 15px; -fx-border-radius: 15px; -fx-height: 100%;"

        fun createButton(buttonTypes: ButtonTypes) {
            val string = getStringNode(buttonTypes)
            val button = Button(string)
            button.style = "-fx-padding: 5px 10px; -fx-margin: 10px;" +
                    " -fx-text-style: bold; -fx-background-color: #778899;"
            button.onAction = EventHandler {
                val node = getNode(buttonTypes)
                node.layoutX += 100
                node.layoutY += 100
                root.children.add(node)
            }
            vBox.children.add(button)
        }

        val buttonTypes: Array<ButtonTypes> = arrayOf(
            ButtonTypes.INT, ButtonTypes.FLOAT, ButtonTypes.STRING,
            ButtonTypes.IMAGE, ButtonTypes.SEPIA, ButtonTypes.GREY,
            ButtonTypes.INVERT, ButtonTypes.BRIGHT, ButtonTypes.GAUSSIAN,
            ButtonTypes.ROTATE, ButtonTypes.SCALE_PIXEL,
            ButtonTypes.SCALE, ButtonTypes.MOVE_PIXEL, ButtonTypes.MOVE,
            ButtonTypes.ADD_TEXT_PIXEL, ButtonTypes.ADD_TEXT
        )

        for (button in buttonTypes) {
            createButton(button)
        }

        return vBox
    }

    private fun getNode(buttonTypes: ButtonTypes): DraggableNode {
        return when (getStringNode(buttonTypes)) {
            "int", IntNode::class.simpleName -> IntNode()
            "float", FloatNode::class.simpleName -> FloatNode()
            "string", StringNode::class.simpleName -> StringNode()
            "image", ImageNodeClass::class.simpleName -> ImageNodeClass()
            "sepia", SepiaNode::class.simpleName -> SepiaNode()
            "grey", GreyNode::class.simpleName -> GreyNode()
            "invert", InvertNode::class.simpleName -> InvertNode()
            "bright", BrightnessNode::class.simpleName -> BrightnessNode()
            "gaussian", GaussianNode::class.simpleName -> GaussianNode()
            "scale pixel", ScalePixelNode::class.simpleName -> ScalePixelNode()
            "scale", ScalePercentNode::class.simpleName -> ScalePercentNode()
            "move pixel", MovePixelsNode::class.simpleName -> MovePixelsNode()
            "move", MovePercentNode::class.simpleName -> MovePercentNode()
            "rotate", RotateNode::class.simpleName -> RotateNode()
            "add text pixel", AddTextPixelNode::class.simpleName -> AddTextPixelNode()
            "add text", AddTextPercentNode::class.simpleName -> AddTextPercentNode()
            EndNode::class.simpleName -> EndNode()
            else -> IntNode()
        }
    }
}

class AwesomePhotoEditor : Application() {
    override fun start(primaryStage: Stage) {
        nu.pattern.OpenCV.loadLocally()
        primaryStage.scene = FaceWindow().start()
        primaryStage.title = "Awesome Photo Editor"
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(AwesomePhotoEditor::class.java)
        }
    }
}