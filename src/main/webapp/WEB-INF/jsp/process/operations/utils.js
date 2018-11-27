function launchLov(lovUrl, windowWidth, windowHeight)
{
  window.open(lovUrl, 'lovWindow', 
              'scrollbars=yes,resizable=yes,height='+windowHeight+',width='+windowWidth);
}

function lovOK(lovList, lovField)
{
  if (lovList.selectedIndex == -1)
  {
    alert("Please select a value");
    return;
  }
  lovField.value = lovList.options[lovList.selectedIndex].text;
  window.close();
}
