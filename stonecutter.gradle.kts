plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "1.21.1-neoforge"

stonecutter parameters {
    constants.match(node.metadata.project.substringAfterLast('-'), "fabric", "forge", "neoforge")

    replacements.string(eval(node.metadata.version, "<1.19")) {
        replace("Component.translatable(", "new net.minecraft.network.chat.TranslatableComponent(")
        replace("ScreenEvent.Init.Post", "ScreenEvent.InitScreenEvent.Post")
        replace("import net.minecraftforge.client.ConfigScreenHandler;", "import net.minecraftforge.client.ConfigGuiHandler;")
        replace("ConfigScreenHandler.ConfigScreenFactory", "ConfigGuiHandler.ConfigGuiFactory")
    }
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.BIN
}
