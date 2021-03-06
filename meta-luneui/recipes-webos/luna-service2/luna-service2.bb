# Copyright (c) 2012-2014 LG Electronics, Inc.

SUMMARY = "Open webOS Luna System Bus library, daemon, and utilities"
AUTHOR = "Keith Derrick <keith.derrick@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "libpbnjson pmloglib glib-2.0 systemd"
VIRTUAL-RUNTIME_cpushareholder ?= "cpushareholder-stub"
VIRTUAL-RUNTIME_rdx-utils ?= "rdx-utils-stub"
RDEPENDS_${PN} = "${VIRTUAL-RUNTIME_cpushareholder} ${VIRTUAL-RUNTIME_rdx-utils}"

PV = "3.9.3-194+git${SRCPV}"
SRCREV = "1b65b37ad8c625cca1d173ec90c99b4a7c576ca2"

WEBOS_DISTRO_PRERELEASE ??= ""
EXTRA_OECMAKE += "${@ '-DWEBOS_DISTRO_PRERELEASE:STRING="devel"' \
                  if d.getVar('WEBOS_DISTRO_PRERELEASE',True) != '' else ''}"

inherit webos_ports_fork_repo
inherit webos_cmake
inherit pkgconfig
inherit webos_system_bus
inherit webos_core_os_dep

SRC_URI = "${WEBOS_PORTS_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

PACKAGECONFIG ??= ""
PACKAGECONFIG[test] = "-DWEBOS_CONFIG_BUILD_TESTS:BOOL=True,,gtest boost,bash"

CFLAGS += "-fgnu89-inline"

# This fix-up will be removed shortly. luna-service2 headers must be included
# using '#include <luna-service2/*.h>'
do_install_append() {
    # XXX Temporarily, create links from the old locations until all users of
    # luna-service2 convert to using pkg-config
    ln -svnf luna-service2/lunaservice.h ${D}${includedir}/lunaservice.h
    ln -svnf luna-service2/lunaservice-errors.h ${D}${includedir}/lunaservice-errors.h
    ln -svnf lib${PN}.so ${D}${libdir}/liblunaservice.so
}

# Disable LTTng tracepoints explicitly.
# LTTng tracepoints in LS2 can cause out of memory, because LS2 is used by many components.
# To enable tracepoints back use WEBOS_LTTNG_ENABLED_pn-luna-service2 = "1"
WEBOS_LTTNG_ENABLED = "0"
EXTRA_OECMAKE += " ${@base_contains('WEBOS_LTTNG_ENABLED', '1', '-DWEBOS_LTTNG_ENABLED:BOOLEAN=True', '', d)}"

WEBOS_DISABLE_LS2_SECURITY ?= "0"
EXTRA_OECMAKE += '${@base_conditional("WEBOS_DISABLE_LS2_SECURITY", "1", "-DWEBOS_LS2_SECURE:BOOLEAN=False", "" ,d)}'
