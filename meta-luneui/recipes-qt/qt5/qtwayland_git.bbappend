SRC_URI = "git://github.com/iscgar/qtwayland.git;protocol=git;branch=add_password_mask_dela"
SRCREV = "ce60662a10bbd28701f1811c91050031fdeae7f1"

FILES_${PN} += "${OE_QMAKE_PATH_PLUGINS}/wayland-graphics-integration"
FILES_${PN}-dbg += "${OE_QMAKE_PATH_PLUGINS}/wayland-graphics-integration/*/.debug"
