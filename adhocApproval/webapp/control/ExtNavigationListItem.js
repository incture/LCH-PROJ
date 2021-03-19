sap.ui.define([
	"sap/tnt/NavigationListItem",
	"sap/ui/core/Renderer"
], function (NavigationListItem, Renderer) {
	"use strict";
	var ExtNavigationListItem = NavigationListItem.extend("com.incture.lch.adhocApproval.control.ExtNavigationListItem", {
		metadata: {
			properties: {
				count: {
					type: "string",
					defaultValue: "0",
					bindable: "bindable"
				}
			}
		}
	});

	ExtNavigationListItem.prototype._renderListItem = function (rm) {
		debugger;
		rm.openStart("div");
		rm.style("width","100%");
		rm.openEnd();
		this._renderText(rm);
		this._renderCount(rm);
		rm.close("div");
	};

	ExtNavigationListItem.prototype._renderText = function (rm) {
		debugger;
		rm.openStart("div");
		rm.style("float","left");
		rm.style("display","inline");
		rm.style("width","70%");
		rm.class("sapMText");
		rm.class("sapTntNavLIText");
		rm.class("sapMTextNoWrap");
		var textDir = this.getTextDirection();
		if (textDir !== sap.ui.core.TextDirection.Inherit) {
			rm.attr("dir", textDir.toLowerCase());
		}
		var textAlign = Renderer.getTextAlign(sap.ui.core.TextAlign.Begin, textDir);
		if (textAlign) {
			rm.style("text-align", textAlign);
		
		}
		rm.openEnd();
		rm.text(this.getText());
		rm.close("div");
	};

	ExtNavigationListItem.prototype._renderCount = function (rm) {
		//rm.write('<div style="float:left; display:inline; width:24%; border-radius: 14%;"');
	
		//rm.addClass("sapMCount");
		debugger;
		rm.openStart("div");
		rm.style("float","left");
		rm.style("display","inline");
		rm.style("width","24%");
		rm.style("border-radius","14%");
		rm.style("text-align","right");
		rm.class("sapTntNavLIText");
		rm.class("sapMTextNoWrap");
		var textAlign = Renderer.getTextAlign(sap.ui.core.TextAlign.Center, "Inherit");
		if (textAlign) {
			rm.style("text-align", textAlign);
		}
		rm.openEnd();
		rm.openStart("bdi");
		rm.class("sapMCount");
		rm.openEnd();
		rm.text(this.getCount());
		rm.close("bdi");
		rm.close("div");
	};

	ExtNavigationListItem.prototype.renderGroupItem = function (rm, control, index, length) {
         debugger;
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
		rm.openStart("div");
		rm.class("sapTntNavLIItem");
		rm.class("sapTntNavLIGroup");
		rm.style("display","inline-flex");
		if (!this.getEnabled()) {
			rm.class("sapTntNavLIItemDisabled");
		} else {
			rm.attr("tabindex","-1");
		}

		if (!isListExpanded || control.hasStyleClass("sapTntNavLIPopup")) {
			tooltip = this.getTooltip_AsString() || text;
			if (tooltip) {
				rm.attr("title", tooltip);
			}

			ariaProps.label = text;
			ariaProps.role = 'menuitem';
			if (!control.hasStyleClass("sapTntNavLIPopup")) {
				ariaProps.haspopup = true;
			}
		} else {
			ariaProps.role = 'treeitem';
		}
		rm.accessibilityState(ariaProps);

		if (control.getExpanded()) {
			tooltip = this.getTooltip_AsString() || text;
			if (tooltip) {
				rm.attr("title", tooltip);
			}
			rm.attr("aria-label", text);
		}
		rm.openEnd();
		this._renderIcon(rm);
		if (control.getExpanded()) {
			var expandIconControl = this._getExpandIconControl();
			expandIconControl.setVisible(this.getItems().length > 0 && this.getHasExpander());
			expandIconControl.setSrc(this.getExpanded() ? NavigationListItem.collapseIcon : NavigationListItem.expandIcon);
			expandIconControl.setTooltip(this._getExpandIconTooltip(!this.getExpanded()));
			this._renderListItem(rm);
			rm.renderControl(expandIconControl);
			
		}
		rm.close("div");
	};

	/**
	 * Renders the second-level navigation item.
	 * @private
	 */
	ExtNavigationListItem.prototype.renderSecondLevelNavItem = function (rm, control, index, length) {
        debugger;
		var group = this.getParent();
		rm.openStart("li");
		rm.class("sapTntNavLIItem");
		rm.class("sapTntNavLIGroupItem");

		if (!this.getEnabled() || !group.getEnabled()) {
			rm.class("sapTntNavLIItemDisabled");
		} else {
			rm.attr("tabindex","-1");
		}

		var text = this.getText();
		var tooltip = this.getTooltip_AsString() || text;
		if (tooltip) {
			rm.attr("title", tooltip);
		}

		rm.accessibilityState({
			role: control.hasStyleClass("sapTntNavLIPopup") ? 'menuitem' : 'treeitem',
			level: '2',
			posinset: index + 1,
			setsize: length
		});

	
		rm.openEnd();
		this._renderListItem(rm);
		rm.close("li");
	};
	
	return ExtNavigationListItem;
});