require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

SUMMARY = "CompuLab 4.1.15 kernel"
DESCRIPTION = "Linux kernel for CompuLab imx6(ul) boards."

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_4.1.15_1.0.0_ga"
SRCREV ?= "77f61547834c4f127b44b13e43c59133a35880dc"
LOCALVERSION = "-cl-1.0"

SRC_URI += "file://0001-platform-add-depends-property-handler.patch"

include linux-compulab_cm-fx6-evk.inc

include linux-compulab_cl-som-imx6ul.inc

include linux-compulab_cl-som-imx7.inc

COMPATIBLE_MACHINE = "(cm-fx6-evk|cl-som-imx6ul|cl-som-imx7)"

inherit fsl-vivante-kernel-driver-handler

IMX_UAPI_HEADERS = "mxc_asrc.h mxc_dcic.h mxcfb.h mxc_mlb.h mxc_sim_interface.h \
                    mxc_v4l2.h ipu.h videodev2.h pxp_device.h pxp_dma.h isl29023.h"

do_install_append () {
   # Install i.MX specific uapi headers
   oe_runmake headers_install INSTALL_HDR_PATH=${B}${exec_prefix}
   install -d ${D}${exec_prefix}/include/linux
   for UAPI_HDR in ${IMX_UAPI_HEADERS}; do
       find ${B}${exec_prefix}/include -name ${UAPI_HDR} -exec cp {} ${D}${exec_prefix}/include/linux \;
       ls ${D}${exec_prefix}/include/linux
       echo "copy ${UAPI_HDR} done"
   done
}

PACKAGES += "linux-imx-soc-headers"
FILES_linux-imx-soc-headers = "${exec_prefix}/include"
