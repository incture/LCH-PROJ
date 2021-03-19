sap.ui.define([
	"sap/tnt/NavigationListItem",
	"sap/ui/core/Renderer"
], function (NavigationListItem, Renderer) {
	"use strict";
	var ExtNavigationListItem = NavigationListItem.extend("com.incture.lch.premfreightOrders.control.ExtNavigationListItem", {
		metadata: {
			properties: {
				count: {
					type: "string",
					defaultValue: "0",
					bindalble: "bindable"
				}
			}
		}
	});

	ExtNavigationListItem.prototype._renderListItem = function (rm) {
		try {
			rm.write('<div style="width:100%">');
			rm.writeEscaped(this._renderText(rm));
			rm.writeEscaped(this._renderCount(rm));
			rm.write("</div>");
		} catch (e) {
			//exception caught
		}
	};

	ExtNavigationListItem.prototype._renderText = function (rm) {
		try {
			rm.write('<div style="float:left; display:inline; width:72%"');
			rm.addClass("sapMText");
			rm.addClass("sapTntNavLIText");
			rm.addClass("sapMTextNoWrap");
			rm.writeClasses();
			var textDir = this.getTextDirection();
			if (textDir !== sap.ui.core.TextDirection.Inherit) {
				rm.writeAttribute("dir", textDir.toLowerCase());
			}
			var textAlign = Renderer.getTextAlign(sap.ui.core.TextAlign.Begin, textDir);
			if (textAlign) {
				rm.addStyle("text-align", textAlign);
				rm.writeStyles();
			}
			rm.write(">");
			rm.writeEscaped(this.getText());
			rm.write("</div>");
		} catch (e) {
			//exception caught
		}
	};

	ExtNavigationListItem.prototype._renderCount = function (rm) {
		try {
			//rm.write('<div style="float:left; display:inline; width:24%; border-radius: 14%;"');
			rm.write('<div style="float:left; display:inline; width:24%; border-radius: 14%; text-align:right"');
			//rm.addClass("sapMCount");
			rm.addClass("sapTntNavLIText");
			rm.addClass("sapMTextNoWrap");
			rm.writeClasses();
			var textAlign = Renderer.getTextAlign(sap.ui.core.TextAlign.Center, "Inherit");
			if (textAlign) {
				rm.addStyle("text-align", textAlign);
				rm.writeStyles();
			}
			rm.write(">");
			rm.write('<bdi');
			rm.addClass("sapMCount");
			rm.writeClasses();
			rm.write('>');
			rm.writeEscaped(this.getCount());
			rm.write("</bdi>");
			rm.write("</div>");
		} catch (e) {
			//exception caught
		}
	};

	ExtNavigationListItem.prototype.renderGroupItem = function (rm, control, index, length) {
		try {
			var isListExpanded = control.getExpanded(),
				isNavListItemExpanded = this.getExpanded(),
				text = this.getText(),
				tooltip,
				ariaProps = {
					level: '1',
					posinset: index + 1,
					setsize: this._getVisibleItems(control).length
				};

			//checking if there are items level 2 in the NavigationListItem
			//of yes - there is need of aria-expanded property
			if (isListExpanded && this.getItems().length !== 0) {
				ariaProps.expanded = isNavListItemExpanded;
			}
			rm.write('<div');
			rm.addClass("sapTntNavLIItem");
			rm.addClass("sapTntNavLIGroup");

			if (!this.getEnabled()) {
				rm.addClass("sapTntNavLIItemDisabled");
			} else {
				rm.write(' tabindex="-1"');
			}

			if (!isListExpanded || control.hasStyleClass("sapTntNavLIPopup")) {
				tooltip = this.getTooltip_AsString() || text;
				if (tooltip) {
					rm.writeAttributeEscaped("title", tooltip);
				}

				ariaProps.label = text;
				ariaProps.role = 'menuitem';
				if (!control.hasStyleClass("sapTntNavLIPopup")) {
					ariaProps.haspopup = true;
				}
			} else {
				ariaProps.role = 'treeitem';
			}
			rm.writeAccessibilityState(ariaProps);

			if (control.getExpanded()) {
				tooltip = this.getTooltip_AsString() || text;
				if (tooltip) {
					rm.writeAttributeEscaped("title", tooltip);
				}
				rm.writeAttributeEscaped("aria-label", text);
			}

			rm.writeClasses();
			rm.write(">");
			this._renderIcon(rm);
			if (control.getExpanded()) {
				var expandIconControl = this._getExpandIconControl();
				expandIconControl.setVisible(this.getItems().length > 0 && this.getHasExpander());
				expandIconControl.setSrc(this.getExpanded() ? NavigationListItem.collapseIcon : NavigationListItem.expandIcon);
				expandIconControl.setTooltip(this._getExpandIconTooltip(!this.getExpanded()));
				this._renderListItem(rm);
				rm.renderControl(expandIconControl);
			}
			rm.write("</div>");

		} catch (e) {
			//exception caught
		}
	};

	/**
	 * Renders the second-level navigation item.
	 * @private
	 */
	ExtNavigationListItem.prototype.renderSecondLevelNavItem = function (rm, control, index, length) {
		try {
			var group = this.getParent();
			rm.write('<li');
			rm.writeElementData(this);
			rm.addClass("sapTntNavLIItem");
			rm.addClass("sapTntNavLIGroupItem");

			if (!this.getEnabled() || !group.getEnabled()) {
				rm.addClass("sapTntNavLIItemDisabled");
			} else {
				rm.write(' tabindex="-1"');
			}

			var text = this.getText();
			var tooltip = this.getTooltip_AsString() || text;
			if (tooltip) {
				rm.writeAttributeEscaped("title", tooltip);
			}

			rm.writeAccessibilityState({
				role: control.hasStyleClass("sapTntNavLIPopup") ? 'menuitem' : 'treeitem',
				level: '2',
				posinset: index + 1,
				setsize: length
			});

			rm.writeClasses();
			rm.write(">");
			this._renderListItem(rm);
			rm.write("</li>");
		} catch (e) {
			//exception caught
		}
	};

	return ExtNavigationListItem;
});