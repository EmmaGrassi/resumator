import React, { PropTypes, Component } from 'react';

export default class PostsComponent extends Component {
  render() {
    return (
      <ul>
        {this.props.posts.map((post, i) =>
          <li key={i}>{post.title}</li>
        )}
      </ul>
    );
  }
}

PostsComponent.propTypes = {
  posts: PropTypes.array.isRequired
};
