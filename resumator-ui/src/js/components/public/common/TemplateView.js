import React from 'react';
import moment from 'moment';

export default function TemplateView(props) {
  return (<div className="template-view" onClick={props.onClick}>
    <img src={props.item.image} />
    <div className="name">{props.item.name}</div>
    <div className="updated">{moment(props.item.lastUpdated).format('dddd, MMMM Do YYYY')}</div>
  </div>);
}
