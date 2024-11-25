package com.rize2knight

import com.cobblemon.mod.common.api.gui.blitk
import com.cobblemon.mod.common.api.moves.MoveTemplate
import com.cobblemon.mod.common.api.moves.categories.DamageCategories
import com.cobblemon.mod.common.api.text.font
import com.cobblemon.mod.common.client.CobblemonClient
import com.cobblemon.mod.common.client.CobblemonResources
import com.cobblemon.mod.common.client.render.drawScaledText
import com.cobblemon.mod.common.util.asTranslated
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.locale.Language
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

object MoveHoverRenderer {

    private val headerLeft = ResourceLocation.fromNamespaceAndPath(CobblemonUITweaksClient.MODID, "textures/battle/move/header/left.png")
    private val headerMiddle = ResourceLocation.fromNamespaceAndPath(CobblemonUITweaksClient.MODID, "textures/battle/move/header/middle.png")
    private val headerRight = ResourceLocation.fromNamespaceAndPath(CobblemonUITweaksClient.MODID, "textures/battle/move/header/right.png")
    private val bodyLeftBorder = ResourceLocation.fromNamespaceAndPath(CobblemonUITweaksClient.MODID, "textures/battle/move/body/left_border.png")
    private val bodyLeftCorner = ResourceLocation.fromNamespaceAndPath(CobblemonUITweaksClient.MODID, "textures/battle/move/body/left_corner.png")
    private val bodyBottomBorder = ResourceLocation.fromNamespaceAndPath(CobblemonUITweaksClient.MODID, "textures/battle/move/body/bottom_border.png")
    private val bodyRightCorner = ResourceLocation.fromNamespaceAndPath(CobblemonUITweaksClient.MODID, "textures/battle/move/body/right_corner.png")
    private val bodyRightBorder = ResourceLocation.fromNamespaceAndPath(CobblemonUITweaksClient.MODID, "textures/battle/move/body/right_border.png")
    private val bodyMiddle = ResourceLocation.fromNamespaceAndPath(CobblemonUITweaksClient.MODID, "textures/battle/move/body/middle.png")

    fun render(context: GuiGraphics, x: Float, y: Float, move: MoveTemplate) {
        val bodyWidth = 150
        val opacity = 0.95

        val basePowerText = if (move.power == 0.0) {
            "cobblemon_ui_tweaks.move.base_power.not_applicable".asTranslated()
        }
        else {
            "cobblemon_ui_tweaks.move.base_power".asTranslated(move.power)
        }

        val accuracyText = if (move.accuracy == -1.0) {
            "cobblemon_ui_tweaks.move.accuracy.cant_miss".asTranslated()
        }
        else {
            "cobblemon_ui_tweaks.move.accuracy".asTranslated("${move.accuracy}%")
        }

        val moveDescription = move.description.font(CobblemonResources.DEFAULT_LARGE)
        val descriptionLines = Minecraft.getInstance().font.splitter.splitLines(moveDescription, 150, moveDescription.style)
        val orderedLines = Language.getInstance().getVisualOrder(descriptionLines)

        val effectivenessText = getMoveEffectiveness(move)

        val bodyHeight = (orderedLines.size * 8) + 8 + if (effectivenessText != null) 8 else 0

        blitk(
            matrixStack = context.pose(),
            texture = headerLeft,
            x = x,
            y = y - bodyHeight - 34 - 4,
            height = 34,
            width = 2,
            alpha = opacity
        )

        blitk(
            matrixStack = context.pose(),
            texture = headerMiddle,
            x = x + 2,
            y = y - bodyHeight - 34 - 4,
            height = 34,
            width = bodyWidth,
            alpha = opacity
        )

        blitk(
            matrixStack = context.pose(),
            texture = headerRight,
            x = x + 2 + bodyWidth,
            y = y - bodyHeight - 34 - 4,
            height = 34,
            width = 2,
            alpha = opacity
        )

        blitk(
            matrixStack = context.pose(),
            texture = bodyLeftBorder,
            x = x,
            y = y - bodyHeight - 4,
            height = bodyHeight,
            width = 2,
            alpha = opacity
        )

        blitk(
            matrixStack = context.pose(),
            texture = bodyLeftCorner,
            x = x,
            y = y - 4,
            height = 4,
            width = 2,
            alpha = opacity
        )

        blitk(
            matrixStack = context.pose(),
            texture = bodyBottomBorder,
            x = x + 2,
            y = y - 4,
            height = 4,
            width = bodyWidth,
            alpha = opacity
        )

        blitk(
            matrixStack = context.pose(),
            texture = bodyRightCorner,
            x = x + bodyWidth + 2,
            y = y - 4,
            height = 4,
            width = 2,
            alpha = opacity
        )

        blitk(
            matrixStack = context.pose(),
            texture = bodyRightBorder,
            x = x + bodyWidth + 2,
            y = y - bodyHeight - 4,
            height = bodyHeight,
            width = 2,
            alpha = opacity
        )

        blitk(
            matrixStack = context.pose(),
            texture = bodyMiddle,
            x = x + 2,
            y = y - bodyHeight - 4,
            height = bodyHeight,
            width = bodyWidth,
            alpha = opacity
        )

        drawScaledText(
            context = context,
            font = CobblemonResources.DEFAULT_LARGE,
            text = move.displayName,
            x = x + 4,
            y = y - bodyHeight - 34 - 4 + 3,
            opacity = opacity
        )

        ScaleableTypeIcon(
            x = x + bodyWidth - 10,
            y = y - bodyHeight - 34 - 4 + 3,
            type = move.elementalType,
            small = true,
            opacity = opacity.toFloat()
        ).render(context)

        drawScaledText(
            context = context,
            font = CobblemonResources.DEFAULT_LARGE,
            text = basePowerText,
            scale = 0.85f,
            x = x + 4,
            y = y - bodyHeight - 34 - 4 + 3 + 14,
            opacity = opacity,
        )

        drawScaledText(
            context = context,
            font = CobblemonResources.DEFAULT_LARGE,
            text = accuracyText,
            scale = 0.85f,
            x = x + 4,
            y = y - bodyHeight - 34 - 4 + 3 + 22,
            opacity = opacity
        )

        orderedLines.forEachIndexed { index, line ->
            drawScaledText(
                context = context,
                text = line,
                scaleX = 0.85f,
                scaleY = 0.85f,
                x = x + 4,
                y = y - bodyHeight - 34 - 4 + 3 + 34 + (index * 8),
                opacity = opacity
            )
        }

        if (effectivenessText != null) {
            drawScaledText(
                context = context,
                font = CobblemonResources.DEFAULT_LARGE,
                text = effectivenessText,
                scale = 0.85f,
                x = x + 4,
                y = y - bodyHeight - 34 - 4 + 3 + 34 + (orderedLines.size * 8) + 4,
                opacity = opacity
            )
        }
    }

    private fun getMoveEffectiveness(move: MoveTemplate): MutableComponent? {
        val battle = CobblemonClient.battle ?: return null
        val opponent = battle.side2.activeClientBattlePokemon.firstOrNull()?.battlePokemon ?: return null
        val opponentForm = opponent.species.getForm(opponent.state.currentAspects)
        if (move.damageCategory == DamageCategories.STATUS) return null
        return MoveEffectivenessCalculator.getMoveEffectiveness(
            move.elementalType,
            opponentForm.primaryType,
            opponentForm.secondaryType
        )
    }

}
